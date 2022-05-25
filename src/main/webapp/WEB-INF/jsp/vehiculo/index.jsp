<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="jf" uri="/WEB-INF/tlds/jform.tld" %>
<!DOCTYPE html>
<html lang="en">
<head>
 <%@include file="/WEB-INF/jsp/include-css.jsp" %>
 <%@include file="/WEB-INF/jsp/include-js.jsp" %>
 <script>

		(function(window, document, $, undefined) {
			$(function() {
				var formVehiculo = $("#formVehiculo");
				
				formVehiculo.submit(function (event) {
                    swal({
                        title: "¿Esta seguro?",
                        text: "¡Se ingresara un nuevo registro!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#5D9CEC",
                        confirmButtonText: "Sí, guardar registro!",
                        cancelButtonText: "No, cancelar!",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            $.ajax({
                                data: formVehiculo.serialize(),
                                url: '${pageContext.request.contextPath}/saveVehiculo',
                                type: "POST",
                                success: function (response) {
                                    swal("Guardado!", "Su registro se guardo exitosamente.", "success");
                                    $("button[data-dismiss='modal']").click();
                                    formVehiculo.trigger("reloadGrid");
                                    return false;
                                },
                                error: function (x, e) {
                                    alert("Ocurrio un error");
                                }
                            });
                        } else {
                            swal("Cancelado", "Su petición ha sido cancelada.", "error");
                            $("button[data-dismiss='modal']").click();
                        }
                    });
                    return false;
                });

			});
		})(window, document, window.jQuery);
	</script>
</head>
<body class="skin-megna fixed-layout">
	<div class="preloader">
		<div class="loader">
			<div class="loader__figure"></div>
			<p class="loader__label">ADMIN</p>
		</div>
	</div>
	<div id="main-wrapper">
		<%@include file="/WEB-INF/jsp/header.jsp" %>
		<%@include file="/WEB-INF/jsp/menu.jsp" %>
		<div class="page-wrapper">
         	<div class="container-fluid">
					<div class="row page-titles">
                        <div class="col-md-5 align-self-center">
                            <h4 class="text-themecolor"></h4>
                        </div>
                        <div class="col-md-7 align-self-center text-right">
                            <div class="d-flex justify-content-end align-items-center">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="javascript:void(0)">Index</a></li>
                                    <li class="breadcrumb-item active">Vehículo</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div>
 					 <div class="jqgrid-responsive mb-lg">
                            <script type="text/javascript">
             (function(window, document, $, undefined){
                 $(function(){
                     var jqGridVehiculo = $("#jqGridVehiculo");
                     var formVehiculo = $("#formVehiculo");
                     var modalVehiculo = $("#modalVehiculo");
                     
                     function deleterow(id) {
                         swal({
                             title: "¿Esta seguro que desea eliminar el registro?",
                             text: "¡Una vez eliminado ya no se podra recuperar!",
                             type: "warning",
                             showCancelButton: true,
                             confirmButtonColor: "#CE1616",
                             confirmButtonText: "Sí, eliminar registro!",
                             cancelButtonText: "No, cancelar!",
                             closeOnConfirm: false,  showLoaderOnConfirm: true,
                             closeOnCancel: false
                             },function (isConfirm) {
                                 if (isConfirm) {
                                     $.ajax({
                                            data: formVehiculo.serialize(),
                                            url: '/springform/deleteVehiculo',
                                            type: "DELETE",
                                            success: function (response) {
                                                 swal("Eliminado!", "Su registro se elimino exitosamente.", "success");
                                                 $("button[data-dismiss='modal']").click();
                                                 jqGridVehiculo.trigger("reloadGrid");
                                                 return false;
                                            }
                                     });swal.close();
                                 } else {
                                         swal("Cancelado", "Su petición ha sido cancelada.", "error");
                                         $("button[data-dismiss='modal']").click();
                                 }
                             });
                             return false;
                     }
                     
                     formVehiculo.submit(function (event) {
                         swal({
                             title: "¿Esta seguro?",
                             text: "¡Se ingresara un nuevo registro!",
                             type: "warning",
                             showCancelButton: true,
                             confirmButtonColor: "#5D9CEC",
                             confirmButtonText: "Sí, guardar registro!",
                             cancelButtonText: "No, cancelar!",
                             closeOnConfirm: false,  showLoaderOnConfirm: true,
                             closeOnCancel: false
                         }, function (isConfirm) {
                                 if (isConfirm) {
                                     $.ajax({
                                         data: formVehiculo.serialize(),
                                         url: '/springform/saveVehiculo',
                                         type: "POST",
                                         success: function (response) {
                                             swal("Guardado!", "Su registro se guardo exitosamente.", "success");
                                             $("button[data-dismiss='modal']").click();
                                             jqGridVehiculo.trigger("reloadGrid");
                                             return false;
                                         },
                                         error: function (x, e) {
                                             alert("Ocurrio un error");
                                         }
                                     });
                                 } else {
                                     swal("Cancelado", "Su petición ha sido cancelada.", "error");
                                     $("button[data-dismiss='modal']").click();
                                 }
                            });
                            return false;
                     });
                     
                    jqGridVehiculo.jqGrid({
                         url: '/springform/gridVehiculo',
                         datatype: "json",
                         colModel:[ 
                                   { label: 'ID', name: 'idCarro', width: 50 ,align:'center'}
                                   ,{ label: 'MARCA', name: 'marca', width: 50 ,align:'center'}
                                   ,{ label: 'PLACA', name: 'placa', width: 50 ,align:'center'}
                                   ,{ label: 'COLOR', name: 'color', width: 50 ,align:'center'}
                                   ,{ label: 'MODELO', name: 'modelo', width: 50 ,align:'center'}
                                                     ],
                         viewrecords: true,
                         autowidth: true,
                         shrinkToFit: true,
                         sortname: "idCarro",
                         sortorder: "desc",
                         height: 240,
                         rowNum: 100,
                         rowList: [100,200,500,1000],
                         loadonce: false,
                         caption: "Tabla de vehículos",
                         hidegrid: false,
                         pager: "#jqGridVehiculoPager"
                    });
                     
                    jqGridVehiculo.jqGrid('filterToolbar', {stringResult: true, searchOnEnter: true, defaultSearch: "cn"});
                     
                     jqGridVehiculo.jqGrid('navGrid', '#jqGridVehiculoPager',
                         {edit: false, add: false, del: false, search: false},
                          {}, {}, {},
                          {// search
                              closeOnEscape: true,
                              multipleSearch: true,
                              closeAfterSearch: true
                      })
                     
                     jqGridVehiculo.jqGrid('navButtonAdd', '#jqGridVehiculoPager', {
                          id: 'pager_preview', caption: '', title: 'Ver',
                              onClickButton: function (e) {
                                  var row = jqGridVehiculo.jqGrid('getGridParam', 'selrow');
                                  if (row != null) {
                                      var rowData = jqGridVehiculo.getRowData(row);
                                      readOnlyForm(formVehiculo, rowData);
                                      $("#btnModalSubmit").prop('disabled', true);
                                       modalVehiculo.modal('toggle');
                                  } else {
                                      swal("Advertencia", "Por favor seleccione una fila.", "warning");
                                  }
                              },
                                  buttonicon: 'ui-icon-preview'
                          }
                     );
                     jqGridVehiculo.jqGrid('navButtonAdd', '#jqGridVehiculoPager', {
                         id: 'pager_save',
                         caption: '',
                         title: 'Agregar',
                         onClickButton: function (e) {
                             resetForm(formVehiculo);
                                 modalVehiculo.modal('toggle');
                         },
                         buttonicon: 'ui-icon-plus'
                     });
                     
                     jqGridVehiculo.jqGrid('navButtonAdd', '#jqGridVehiculoPager', {
                                 id: 'pager_edit',
                                 caption: '',
                                 title: 'Editar',
                                 onClickButton: function (e) {
                                      var row = jqGridVehiculo.jqGrid('getGridParam', 'selrow');
                                      if (row != null) {
                                         var rowData = jqGridVehiculo.getRowData(row);
                                         populateForm(formVehiculo, rowData);
                                         modalVehiculo.modal('toggle');
                                     } else {
                                         swal("Advertencia", "Por favor seleccione una fila.", "warning");
                                     }
                                 },
                                 buttonicon: 'ui-icon ui-icon-pencil'
                             });
                     
                     jqGridVehiculo.jqGrid('navButtonAdd', '#jqGridVehiculoPager', {
                         id: 'pager_delete',
                         caption: '',
                         title: 'Eliminar',
                         onClickButton: function (e) {
                             var row = jqGridVehiculo.jqGrid('getGridParam', 'selrow');
                             if (row != null) {
                                 var rowData = jqGridVehiculon.getRowData(row);
                                  populateForm(formVehiculo, rowData);
                                 deleterow();
                             } else {
                                 swal("Advertencia", "Por favor seleccione una fila.", "warning");
                             }
                         },
                         buttonicon: 'ui-icon ui-icon-trash'
                      });
                     jqGridVehiculo.jqGrid('navButtonAdd', '#jqGridVehiculoPager', {
                         id: 'pager_excel',
                         caption: '',
                         title: 'Export',
                         onClickButton: function (e) {
                              exportVehiculoGrid();
                         },
                         buttonicon: 'ui-icon-calculator'
                     });
                     
                      function exportVehiculoGrid() 
{                          $('#filters').val(jqGridVehiculo.jqGrid('getGridParam', 'postData').filters);
                          $('#jqGridVehiculoGridParameters').submit();
                                 }
                     
                      $(window).on('resize', function () {
                          var width = $('.jqgrid-responsive').width();
                          jqGridVehiculo.setGridWidth(width);
                      }).resize();
                     
                  });
               })(window, document, window.jQuery);
               </script>
                     
               <table id="jqGridVehiculo"></table>
               <table id="jqGridVehiculoPager"></table>
               
               <form method="post" id="jqGridVehiculoGridParameters" action="/springform/exportVehiculo"  target="_blank">
                  <input type="hidden" name="filters" id="filters" value=""/>
                  <input type="hidden" id="jqgridExport"/> 
               </form>
               

					</div>
                </div>
            </div>
            
	</div>
	  <!-- Modal-->
        <div id="modalVehiculo" tabindex="-1" role="dialog" aria-labelledby="myModalLabelLarge" aria-hidden="true" class="modal fade">
           <div class="modal-dialog modal-lg">
                <div class="modal-content">
  
                    <div class="modal-body">
                       <form method="POST" id="formVehiculo" action="${urlsaveVehiculo}" data-parsley-validate="" novalidate="" class="form-horizontal">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">ID</label>
                                         <div class="col-lg-10">
										
                                         <jf:numberbox    precision="10"    id="idCarro" name="idCarro"  readonly="true"/>
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">MARCA</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="1" maxlength="255"   id="marca" name="marca" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">PLACA</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="2" maxlength="255"   id="placa" name="placa" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">COLOR</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="3" maxlength="100"   id="color" name="color" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">MODELO</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="5" maxlength="255"   id="modelo" name="modelo" />
                                         </div>
                                     </div> 
                                <div class="panel-footer text-right">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
                                    <button type="submit" class="btn btn-">Run validation</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
</body>
</html>