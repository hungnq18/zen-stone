

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- footer -->
<div class="footer">
    <div class="wthree-copyright">
        <p>© 
            <script>
                let date = new Date();
                document.write(date.getFullYear());
            </script>
            | Design by <a href="/admin"> Group 5</a></p>
    </div>
</div>
<!-- / footer -->
</section>
<!--main content end-->
</section>
<script src="./admin/assets/js/bootstrap.js"></script>
<script src="./admin/assets/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="./admin/assets/js/scripts.js"></script>
<script src="./admin/assets/js/jquery.slimscroll.js"></script>
<script src="./admin/assets/js/jquery.nicescroll.js"></script>
<script src="./admin/assets/js/jquery.scrollTo.js"></script>
<script src="./admin/assets/js/toast.js"></script>
<script src="./admin/assets/js/app.js"></script> 
<c:set value="${param.status}" var="status" ></c:set>
<c:set value="${param.message}" var="message" ></c:set>
<c:if test="${status == 1}">
    <script>
                showSuccess("${message != null ? message : 'Action success'}");
    </script>
</c:if>
<c:if test="${status != null && status != 1}">
    <script>
        showError("${message != null ? message : 'Action fail'}");
    </script>
</c:if>
<script>

    let checkAll = document.querySelector(".all-check input[type='checkbox']");
    let inputItemCheck = document.querySelectorAll("table tr td input[type='checkbox']");
    if (checkAll) {
        checkAll.addEventListener('click', () => {
            inputItemCheck.forEach((item) => {
                if (checkAll.checked == true) {
                    item.checked = true;
                } else {
                    item.checked = false;
                }
            })
        })
    }
</script>
</body>
</html>
