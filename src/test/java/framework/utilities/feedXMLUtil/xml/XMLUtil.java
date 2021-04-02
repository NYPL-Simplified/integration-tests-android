package framework.utilities.feedXMLUtil.xml;

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

    private HashMap<String, List<BookModel>> hashMapAvailableEbooks;
    private HashMap<String, List<BookModel>> hashMapAvailableAudiobooks;
    private HashMap<String, List<BookModel>> hashMapUnavailableEbooks;
    private HashMap<String, List<BookModel>> hashMapUnavailableAudiobooks;

    private ArrayList<BookModel> availableBooksAnyType;
    private ArrayList<BookModel> unavailableBooksAnyType;
    private static XMLUtil xmlUtil;

    private XMLUtil() {
        setHashMapsForEBooksAndAudioBooks();
    }

    public static XMLUtil getInstance(){
        if(xmlUtil == null){
            xmlUtil = new XMLUtil();
        }
        return xmlUtil;
    }

    public static void instanceInitialization(){
        if(xmlUtil == null){
            xmlUtil = new XMLUtil();
        }
    }

    //1
    private void setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat() {
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
    public String getRandomBook(String availabilityType, String bookType, String distributor) {
        HashMap<String, List<BookModel>> hashMap = null;
        if(availabilityType.toLowerCase().equals("available")){
            if (bookType.toLowerCase().equals("ebook")) {
                hashMap = hashMapAvailableEbooks;
            } else if (bookType.toLowerCase().equals("audiobook")) {
                hashMap = hashMapAvailableAudiobooks;
            }
        }else if(availabilityType.toLowerCase().equals("unavailable")){
            if (bookType.toLowerCase().equals("ebook")) {
                hashMap = hashMapUnavailableEbooks;
            } else if (bookType.toLowerCase().equals("audiobook")) {
                hashMap = hashMapUnavailableAudiobooks;
            }
        }
        if(!hashMap.containsKey(distributor.toLowerCase())){
            throw new RuntimeException("There is not " + availabilityType + " books for distributor: " + distributor);
        }

        if(hashMap.get(distributor.toLowerCase()).size() == 0){
            throw new RuntimeException("hashMap.get(distributor.toLowerCase()).size() == 0 and distributor: " + distributor + " bookType: " + bookType);
        }
        //System.out.println("hashMap.get(distributor.toLowerCase()).size() == " + hashMap.get(distributor.toLowerCase()).size() + " distributor " + distributor);

        String bookName = hashMap.get(distributor.toLowerCase()).get(RandomUtils.nextInt(0, hashMap.get(distributor.toLowerCase()).size())).getBookName();

       /* List<BookModel> list = hashMap.get(distributor.toLowerCase());
        for (int i = 0; i < list.size(); i++) {
            BookModel bookModel = list.get(i);
            if (bookModel.getBookName().toLowerCase().equals(bookName.toLowerCase())) {
                list.remove(bookModel);
                hashMap.put(distributor.toLowerCase(), list);
                break;
            }
        }*/
        return bookName;
    }

    //1
    private void setHashMapsForEBooksAndAudioBooks() {
        setListAvailableAndUnavailableBooksAnyTypeMayBeWithRepeat();
        hashMapAvailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, "ebook");
        hashMapAvailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(availableBooksAnyType, "audiobook");
        hashMapUnavailableEbooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, "ebook");
        hashMapUnavailableAudiobooks = getHashMapForAvailableAndUnavailableBooksWithSpecificType(unavailableBooksAnyType, "audiobook");
    }

    //1
    private HashMap<String, List<BookModel>> getHashMapForAvailableAndUnavailableBooksWithSpecificType(ArrayList<BookModel> arrayList, String bookType) {

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
    private FeedModel getFeedModel(String url) {
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
