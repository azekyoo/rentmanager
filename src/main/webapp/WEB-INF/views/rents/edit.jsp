<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<c:set var="reservation" value="${requestScope.reservation}" />

<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Modification de la reservation
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->

                        <!-- ATTENTION SE RÉFERER AU FORMULAIRE DE CREATION DE RESERVATION -->

                        <form class="form-horizontal" method="post">
                            <input type="hidden" name="id" value="${reservation.id}">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Voiture: </label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="car" name="vehicle_id" required>
                                            <option value="">Select Vehicule</option>
                                            <c:forEach items="${vehicles}" var="car">
                                                <option value="${car.id}" <c:if test="${car.id eq reservation.vehicle_id}">selected</c:if>>${car.constructeur} ${car.modele}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client: </label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client_id" required>
                                            <option value="">Select Client</option>
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.id}" <c:if test="${client.id eq reservation.client_id}">selected</c:if>>${client.nom} ${client.prenom}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Date de debut: </label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="begin" name="debut" required
                                               data-inputmask="'alias': 'dd/mm/yyyy'" data-mask value="${formattedDebut}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">Date de fin: </label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="end" name="fin" required
                                               data-inputmask="'alias': 'dd/mm/yyyy'" data-mask value="${formattedFin}">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
