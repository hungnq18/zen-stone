
package Util;

/**
 *
 * @author Le Tan Kim
 */
public class Pagination {

    public String generatePagination(int currentPage, int totalPages, String typePage, String... key) {
        String keyPath = String.join("", key);
        keyPath = keyPath.equals("") ? "" : "?keyword=" + keyPath;
        int maxVisiblePages = 5; // Số lượng liên kết phân trang hiển thị tối đa
        int pagesToShowOnEachSide = maxVisiblePages / 2;
        StringBuilder pagination = new StringBuilder();
        pagination.append("<div class=\"pagination\">");
        pagination.append("<nav>");
        pagination.append("<ul class=\"pagination-list\">");
        // Generate the "Previous" link
        if (currentPage > 1) {
            pagination.append("<li class=\"pagination-item\"><a class=\"pagination-link\" href=\"" + typePage + "/page-" + (currentPage - 1) + keyPath + "\">«</a></li>");
        }
        // Generate page links
        for (int page = 1; page <= totalPages; page++) {
            if (page == 1 || page == totalPages || (page >= currentPage - pagesToShowOnEachSide && page <= currentPage + pagesToShowOnEachSide)) {
                if (page == currentPage) {
                    pagination.append("<li class=\"pagination-item active\"><a class=\"pagination-link\" href=\"" + typePage + "/page-" + page + keyPath + "\">" + page + "</a></li>");
                } else {
                    pagination.append("<li class=\"pagination-item\"><a class=\"pagination-link\" href=\"" + typePage + "/page-" + page + keyPath + "\">" + page + "</a></li>");
                }
            } else if (pagination.lastIndexOf("...") != pagination.length() - 3) {
                pagination.append("<li class=\"pagination-item\"><span class=\"pagination-link\">...</span></li>");
            }
        }
        // Generate the "Next" link
        if (currentPage < totalPages) {
            pagination.append("<li class=\"pagination-item\"><a class=\"pagination-link\" href=\"" + typePage + "/page-" + (currentPage + 1) + keyPath + "\">»</a></li>");
        }
        pagination.append("</ul>");
        pagination.append("</nav>");
        pagination.append("</div>");
        return pagination.toString();
    }
}
