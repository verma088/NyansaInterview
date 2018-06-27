
/**
 * @author Arvind Verma
 * Time Complexity: O(M)*O(N log(N)) where M is the number of days and N is the number of unique URL in one day
 */
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NyansaInterview {

    TreeMap<Date, HashMap<String, Integer>> dayCount;
    // send output here
    private static PrintWriter out;
    private static final String CHARSET_NAME = "UTF-8";
    private static SimpleDateFormat timeConverter;

    // this is called before invoking any methods
    static {
        try {
            out = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }

    NyansaInterview() {
        dayCount = new TreeMap();
        timeConverter = new SimpleDateFormat("dd/MM/yyyy z");
        timeConverter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    // ********
    // To read the file and store the data line by line in dayCount
    // Time Complexity:
    // O(K) where K is the number of entries in the input file. 
    // ********
    private void ReadFile(String filename) {
        Scanner reader = null;
        Date date;
        String url;
        try {
            reader = new Scanner(new File(filename)); // open file and read data
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split("\\|");
                url = line[1];
                date = new Date(Long.parseLong(line[0]) * 1000L);
                try {
                    date = timeConverter.parse(timeConverter.format(date));
                } catch (ParseException ex) {
                    System.out.println("Cannot convert date.");
                }
                if (!dayCount.containsKey(date)) {
                    dayCount.put(date, new HashMap<>());
                    dayCount.get(date).put(url, 1);
                } else {
                    if (dayCount.get(date).containsKey(url)) {
                        int urlCount = dayCount.get(date).get(url);
                        dayCount.get(date).put(url, (urlCount + 1));
                    } else {
                        dayCount.get(date).put(url, 1);
                    }
                }
            }
        } catch (IOException ex) {
        } finally {
            reader.close();
        }
    }

    // ********
    // Print the data on console from the dayCount
    // Time complexity of GetData
    // O(N Log N) is to sort the URL according to the number of times URL was hit on that particular date.
    // O(M) is to iterate over the dates in dayCount. 
    // O(M)*O(N log(N)) where M is the number of days and N is the number of url in the sortedUrl
    // ********
    private void GetData() {
        for (Map.Entry<Date, HashMap<String, Integer>> singleData : dayCount.entrySet()) {
            Date date = singleData.getKey();
            System.out.println(timeConverter.format(date));
            List sortedUrl = new LinkedList(dayCount.get(date).entrySet());
            Collections.sort(sortedUrl, (Object o1, Object o2) -> ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo((((Map.Entry) (o1)).getValue())));
            for (Iterator it = sortedUrl.iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                out.print(entry.getKey() + " ");
                out.println(entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        NyansaInterview obj = new NyansaInterview();
        obj.ReadFile(args[0]);
        obj.GetData();
    }
}
