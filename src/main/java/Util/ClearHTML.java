package Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author Le Tan Kim
 */
public class ClearHTML {

    public String clearHtml(String input) {
        Document dirtyDoc = Jsoup.parse(input);
        Document cleanDoc = new Cleaner(Whitelist.none()).clean(dirtyDoc);
        System.out.println(cleanDoc.body().html());
        return cleanDoc.body().html();
    }
}
