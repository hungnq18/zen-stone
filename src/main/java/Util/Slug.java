package Util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author HP
 */
public class Slug {

    public String toSlug(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String stringWithoutDiacritics = pattern.matcher(normalizedString).replaceAll("");
        String[] search = {
            "(à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ)",
            "(è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ)",
            "(ì|í|ị|ỉ|ĩ)",
            "(ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ)",
            "(ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ)",
            "(ỳ|ý|ỵ|ỷ|ỹ)",
            "(đ)",
            "(À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ)",
            "(È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ)",
            "(Ì|Í|Ị|Ỉ|Ĩ)",
            "(Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ)",
            "(Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ)",
            "(Ỳ|Ý|Ỵ|Ỷ|Ỹ)",
            "(Đ)",
            "[^a-zA-Z0-9\\-\\_]"
        };
        String[] replace = {
            "a",
            "e",
            "i",
            "o",
            "u",
            "y",
            "d",
            "A",
            "E",
            "I",
            "O",
            "U",
            "Y",
            "D",
            "-"
        };
        for (int i = 0; i < search.length; i++) {
            stringWithoutDiacritics = stringWithoutDiacritics.replaceAll(search[i], replace[i]);
        }
        stringWithoutDiacritics = stringWithoutDiacritics.replaceAll("(-)+", "-");
        stringWithoutDiacritics = stringWithoutDiacritics.toLowerCase();
        return stringWithoutDiacritics;
    }
}
