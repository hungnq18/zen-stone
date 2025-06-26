<%-- 
    Document   : updateCategory
    Created on : Oct 1, 2023, 12:23:25 PM
    Author     : Le Tan Kim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../components/header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section id="main-content">
    <section class="wrapper">
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        Update category
                    </header>
                    <c:set var="producer" value="${currentProducer}"></c:set>
                        <div class="panel-body">
                            <div class=" form">
                                <form class="cmxform form-horizontal" method="post" action="/admin/producer">
                                    <div class="form-group ">
                                        <label for="cname" class="control-label col-lg-3">Name (required)</label>
                                        <div class="col-lg-6">
                                            <input value="${producer.name}" class=" form-control" id="name" name="name" required>
                                        <input value="${producer.ID}" type="hidden" name="id">
                                        <span class="message_error"></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-3">Status</label>
                                    <div class="col-lg-6">
                                        <select name="status" class="form-control">
                                            <option value="1"
                                                    <c:if test="${producer.status == 1}">selected</c:if>
                                                        >Active
                                                    </option>
                                                    <option value="0"
                                                    <c:if test="${producer.status == 0}">selected</c:if>>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-lg-offset-3 col-lg-6">
                                            <button class="btn btn-primary" id="update-producer" name="btn-update-producer" type="submit">Save</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <a class="btn btn-info btn-back-to-page" href="/admin/producer"><< Back to producer</a>
                        </div>
                    </section>
                </div>
            </div>
        </section>
        <script src="./admin/assets/js/validation.js"></script>
        <script >
            const name = document.getElementById('name');
            const nameMessage = "This field can not empty";
            // array to save all input to check, message show error of each and a string regex to check 
            const inputsToValidate = [
                {element: name, message: nameMessage, regex: /^.{1,}$/},
            ];
            validation(inputsToValidate, document.querySelector("#update-producer"));
            </script >
        <%@include file="../components/footer.jsp" %>
