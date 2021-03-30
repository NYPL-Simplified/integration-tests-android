package util.xml;

import aquality.appium.mobile.application.AqualityServices;
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
    private static ArrayList<BookModel> listAvailableBooksMayBeWithRepeatAnyType;

    private XMLUtil() {

    }
    //1
    private static ArrayList<BookModel> getListAvailableBooksMayBeWithRepeatAnyType() {
        String url = "lyrasis/crawlable";
        ArrayList<BookModel> listAvailableBooksAnyType = new ArrayList<>();

        while (true) {
            FeedModel feedModel = getFeedModel(url);
            boolean isNextXMLPresent = feedModel.getLinksFromFeed().stream().anyMatch(link -> link.getConditionForNextXML().equals("next"));
            if (!isNextXMLPresent) {
                break;
            }

            for (Entry entry : feedModel.getEntries()) {
                boolean isCopiesPresent = entry.getLinksFromEntry().stream().anyMatch(link -> link.getCopies() != null);
                if (!isCopiesPresent){
                    continue;
                }
                int countAvailableCopies = entry.getLinksFromEntry().stream().filter(link -> link.getCopies() != null).findFirst().get().getCopies().getCountAvailableCopies();

                if (countAvailableCopies > 0) {
                    String[] arrayBookType = entry.getBookType().split("/");
                    String bookType = arrayBookType[arrayBookType.length - 1];
                    listAvailableBooksAnyType.add(new BookModel(entry.getDistributor().getDistributorName().toLowerCase(), bookType.toLowerCase(), entry.getBookName(), countAvailableCopies));
                }
            }

            String nextUrl = feedModel.getLinksFromFeed().stream().filter(link -> link.getConditionForNextXML().equals("next")).findFirst().get().getNextURLForXML();
            url = nextUrl.replace("https://demo.lyrasistechnology.org/", "");
        }

        return listAvailableBooksAnyType;
    }

    //2
    public static String getRandomBookWithSpecificBookType(String bookType, String distributor) {
        HashMap<String, List<BookModel>> hashMap = getHashMapWithBooksAndDistributorsAndSpecificBookType(bookType.toLowerCase());
        String bookName = hashMap.get(distributor.toLowerCase()).get(RandomUtils.nextInt(0, hashMap.get(distributor.toLowerCase()).size())).getBookName();

        return bookName;
    }

    //1
    public static void setListAvailableBooksMayBeWithRepeatAnyType(){
        //TODO: calculate how much time does it take.
        /*LocalTime localTime = LocalTime.now();
        ForStoreModel forStoreModel = new ForStoreModel();
        forStoreModel.listAvailableBooksMayBeWithRepeatAnyType = getListAvailableBooksMayBeWithRepeatAnyType();
        LocalTime localTime2 = LocalTime.now();
        int dif = localTime2.getMinute() - localTime.getMinute();
        System.out.println("dif: " + dif);
        //сериализация
        //десериализация

        //ser
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:/Ser/new.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.writeObject(forStoreModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("D:/Ser/new.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForStoreModel forStoreModelFinal = null;

        try {
            forStoreModelFinal = (ForStoreModel) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listAvailableBooksMayBeWithRepeatAnyType = forStoreModelFinal.listAvailableBooksMayBeWithRepeatAnyType;
    }

    //2
    private static HashMap<String, List<BookModel>> getHashMapWithBooksAndDistributorsAndSpecificBookType(String bookType) {
        /*FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("D:/Ser/new.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForStoreModel forStoreModelFinal = null;

        try {
            forStoreModelFinal = (ForStoreModel) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //ArrayList<BookModel> arrayList = forStoreModelFinal.listAvailableBooksMayBeWithRepeatAnyType;
        ArrayList<BookModel> arrayList = XMLUtil.listAvailableBooksMayBeWithRepeatAnyType;

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
