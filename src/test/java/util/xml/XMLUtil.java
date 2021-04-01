package util.xml;

import okhttp3.OkHttpClient;
import org.apache.commons.lang3.RandomUtils;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class XMLUtil {
    private static final String BASE_URL = "https://demo.lyrasistechnology.org";
    public static int sch = 0;
    private static HashMap<String, List<BookModel>> hashMapAvailableEbooks;
    private static HashMap<String, List<BookModel>> hashMapAvailableAudiobooks;
    private static HashMap<String, List<BookModel>> hashMapUnavailableEbooks;
    private static HashMap<String, List<BookModel>> hashMapUnavailableAudiobooks;

    private static ArrayList<BookModel> availableBooksAnyType;
    private static ArrayList<BookModel> unavailableBooksAnyType;

    private XMLUtil() {

    }

    //1
    private static void setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat() {
        String url = "lyrasis/crawlable";
        ArrayList<BookModel> listAvailableBooksAnyType = new ArrayList<>();
        ArrayList<BookModel> listUnavailableBooksAnyType = new ArrayList<>();

        LocalTime localTime = LocalTime.now();
        while (true) {
            FeedModel feedModel = getFeedModel(url);
            boolean isNextXMLPresent = feedModel.getLinksFromFeed().stream().anyMatch(link -> link.getConditionForNextXML().equals("next"));
            if (!isNextXMLPresent) {
                break;
            }

            for (Entry entry : feedModel.getEntries()) {
                boolean isCopiesPresent = entry.getLinksFromEntry().stream().anyMatch(link -> link.getCopies() != null);
                if (!isCopiesPresent) {
                    continue;
                }
                int countAvailableCopies = entry.getLinksFromEntry().stream().filter(link -> link.getCopies() != null).findFirst().get().getCopies().getCountAvailableCopies();

                if (countAvailableCopies > 0) {
                    String[] arrayBookType = entry.getBookType().split("/");
                    String bookType = arrayBookType[arrayBookType.length - 1];
                    listAvailableBooksAnyType.add(new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), countAvailableCopies));
                } else if (countAvailableCopies == 0) {
                    String[] arrayBookType = entry.getBookType().split("/");
                    String bookType = arrayBookType[arrayBookType.length - 1];
                    listUnavailableBooksAnyType.add(new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), countAvailableCopies));
                }
            }

            String nextUrl = feedModel.getLinksFromFeed().stream().filter(link -> link.getConditionForNextXML().equals("next")).findFirst().get().getNextURLForXML();
            url = nextUrl.replace("https://demo.lyrasistechnology.org/", "");
        }
        LocalTime localTime2 = LocalTime.now();
        int dif = localTime2.getMinute() - localTime.getMinute();
        System.out.println("dif: " + dif);

        availableBooksAnyType = listAvailableBooksAnyType;
        unavailableBooksAnyType = listUnavailableBooksAnyType;
    }

    //2
    public static String getAvailableBookSpecificType(String bookType, String distributor) {
        HashMap<String, List<BookModel>> hashMap = null;
        if (bookType.toLowerCase().equals("ebook")) {
            hashMap = hashMapAvailableEbooks;
        } else if (bookType.toLowerCase().equals("audiobook")) {
            hashMap = hashMapAvailableAudiobooks;
        }
        String bookName = hashMap.get(distributor.toLowerCase()).get(RandomUtils.nextInt(0, hashMap.get(distributor.toLowerCase()).size())).getBookName();

        List<BookModel> list = hashMap.get(distributor);
        for (int i = 0; i < list.size(); i++) {
            BookModel bookModel = list.get(i);
            if (bookModel.getBookName().toLowerCase().equals(bookName.toLowerCase())) {
                list.remove(bookModel);
                break;
            }
        }
        return bookName;
    }

    //2
    public static String getUnavailableBookSpecificType(String bookType, String distributor) {
        HashMap<String, List<BookModel>> hashMap = null;
        if (bookType.toLowerCase().equals("ebook")) {
            hashMap = hashMapUnavailableEbooks;
        } else if (bookType.toLowerCase().equals("audiobook")) {
            hashMap = hashMapUnavailableAudiobooks;
        }
        String bookName = hashMap.get(distributor.toLowerCase()).get(RandomUtils.nextInt(0, hashMap.get(distributor.toLowerCase()).size())).getBookName();

        List<BookModel> list = hashMap.get(distributor);
        for (int i = 0; i < list.size(); i++) {
            BookModel bookModel = list.get(i);
            if (bookModel.getBookName().toLowerCase().equals(bookName.toLowerCase())) {
                list.remove(bookModel);
                break;
            }
        }

        return bookName;
    }

    //1
    public static void setHashMapsForEBooksAndAudioBooks() {
        setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat();
        hashMapAvailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, "ebook");
        hashMapAvailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, "audiobook");
        hashMapUnavailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, "ebook");
        hashMapUnavailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, "audiobook");
    }

    //1
    private static HashMap<String, List<BookModel>> getHashMapForAvailableAndUnavailableBooksWithSpecificType(ArrayList<BookModel> arrayList, String bookType) {

        Set<String> setDistributors = arrayList.stream().map(book -> book.getDistributorName()).collect(Collectors.toSet());

        HashMap<String, List<BookModel>> hashMap = new HashMap<>();

        for (String distributor : setDistributors) {
            Set<BookModel> setBooks = arrayList.stream().filter(book -> book.getDistributorName().toLowerCase().equals(distributor.toLowerCase())).collect(Collectors.toSet());
            Set<BookModel> setBooksSpecificBookType = setBooks.stream().filter(book -> book.getBookType().toLowerCase().equals(bookType.toLowerCase())).collect(Collectors.toSet());
            hashMap.put(distributor.toLowerCase(), new ArrayList<>(setBooksSpecificBookType));
        }

        return hashMap;
    }

    //1
    private static FeedModel getFeedModel(String url) {
        BookAPIMethods trainAPIMethods = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JaxbConverterFactory.create()).
                client(new OkHttpClient()).build().create(BookAPIMethods.class);
        Response<FeedModel> response = null;
        try {
            response = trainAPIMethods.getFeedModel(url).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.body();
    }
}
