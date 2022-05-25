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
				var formPerson = $("#formPerson");
				
				formPerson.submit(function (event) {
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
                                data: formPerson.serialize(),
                                url: '${pageContext.request.contextPath}/savePerson',
                                type: "POST",
                                success: function (response) {
                                    swal("Guardado!", "Su registro se guardo exitosamente.", "success");
                                    $("button[data-dismiss='modal']").click();
                                    formPerson.trigger("reloadGrid");
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
                                    <li class="breadcrumb-item active">Person</li>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div>
 					 <div class="jqgrid-responsive mb-lg">
                            <script type="text/javascript">
             (function(window, document, $, undefined){
                 $(function(){
                     var jqGridPerson = $("#jqGridPerson");
                     var formPerson = $("#formPerson");
                     var modalPerson = $("#modalPerson");
                     
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
                                            data: formPerson.serialize(),
                                            url: '/springform/deletePerson',
                                            type: "DELETE",
                                            success: function (response) {
                                                 swal("Eliminado!", "Su registro se elimino exitosamente.", "success");
                                                 $("button[data-dismiss='modal']").click();
                                                 jqGridPerson.trigger("reloadGrid");
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
                     
                     formPerson.submit(function (event) {
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
                                         data: formPerson.serialize(),
                                         url: '/springform/savePerson',
                                         type: "POST",
                                         success: function (response) {
                                             swal("Guardado!", "Su registro se guardo exitosamente.", "success");
                                             $("button[data-dismiss='modal']").click();
                                             jqGridPerson.trigger("reloadGrid");
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
                     
                    jqGridPerson.jqGrid({
                         url: '/springform/gridPerson',
                         datatype: "json",
                         colModel:[ 
                                   { label: 'ID', name: 'idPerson', width: 50 ,align:'center'}
                                   ,{ label: 'NOMBRE', name: 'nombre', width: 50 ,align:'center'}
                                   ,{ label: 'DIRECCION', name: 'direccion', width: 50 ,align:'center'}
                                   ,{ label: 'TELEFONO', name: 'telefono', width: 50 ,align:'center'}
                                   ,{ label: 'CORREO', name: 'correo', width: 50 ,align:'center'}
                                   ,{ label: 'FECHA CREACION', name: 'fechaCreacion', width: 50 ,align:'center', searchoptions: {
                                           dataInit: function (el) {
                                               $(el).keydown(function (e) {
                                                   if ($(this).val() == "__/__/____") {
                                                       $(this).val("");
                                                   }
                                               });
                                               $(el).mask("99/99/9999");
                                               $(el).datepicker({dateFormat: 'dd/mm/yy',
                                                   onSelect: function (dateText, inst) {
                                                       jqGridPerson[0].triggerToolbar();
                                                   },
                                                   beforeShow: function (input, inst) {
                                                       setTimeout(function () {$(".ui-datepicker").css("z-index", 2000);}, 10);
                                                   }
                                               });
                                           }
                                       }
                                   }
                                   ,{ label: 'ESPECIALIDAD', name: 'especialidad', width: 50 ,align:'center'}
                                   ,{ label: 'DEPARTAMENTO', name: 'catDepartamentoDelegate',align:'center', width: 50,hidden:true},
									{ label: 'DEPARTAMENTO', name: 'catDepartamentoDescriptionDelegate',index: 'departamento.idDepartamento',align:'center', width: 50,stype:"select",
                                           searchoptions: {
                                               dataUrl: '${pageContext.request.contextPath}/cbofilterDepartamento',
                                               buildSelect: function (response) {
                                                   var obj = $.parseJSON(response);
                                                   var html = "<select><option></option>";
                                                   for (i in obj)
                                                       html += "<option value=" + obj[i].value + ">" + obj[i].description + "</option>";
                                                       html += "</select>";
                                                       return html;
                                               },
                                               dataInit: function (element) {
                                                   $(element).select2({placeholder: "Todos", allowClear: true});
                                               }
                                           }
                                      }
                                   ,{ label: 'MUNICIPIO', name: 'municipioDelegate',align:'center', width: 50,hidden:true},
                                    { label: 'MUNICIPIO', name: 'municipioDescriptionDelegate',index: 'municipio.idMunicipio',align:'center', width: 50,stype:"select",
                                           searchoptions: {
                                               dataUrl: '${pageContext.request.contextPath}/cbofilterMunicipio',
                                               buildSelect: function (response) {
                                                   var obj = $.parseJSON(response);
                                                   var html = "<select><option></option>";
                                                   for (i in obj)
                                                       html += "<option value=" + obj[i].value + ">" + obj[i].description + "</option>";
                                                       html += "</select>";
                                                       return html;
                                               },
                                               dataInit: function (element) {
                                                   $(element).select2({placeholder: "Todos", allowClear: true});
                                               }
                                           }
                                      }                         ],
                         viewrecords: true,
                         autowidth: true,
                         shrinkToFit: true,
                         sortname: "idPerson",
                         sortorder: "desc",
                         height: 240,
                         rowNum: 100,
                         rowList: [100,200,500,1000],
                         loadonce: false,
                         caption: "Person grid",
                         hidegrid: false,
                         pager: "#jqGridPersonPager"
                    });
                     
                    jqGridPerson.jqGrid('filterToolbar', {stringResult: true, searchOnEnter: true, defaultSearch: "cn"});
                     
                     jqGridPerson.jqGrid('navGrid', '#jqGridPersonPager',
                         {edit: false, add: false, del: false, search: false},
                          {}, {}, {},
                          {// search
                              closeOnEscape: true,
                              multipleSearch: true,
                              closeAfterSearch: true
                      })
                     
                     jqGridPerson.jqGrid('navButtonAdd', '#jqGridPersonPager', {
                          id: 'pager_preview', caption: '', title: 'Ver',
                              onClickButton: function (e) {
                                  var row = jqGridPerson.jqGrid('getGridParam', 'selrow');
                                  if (row != null) {
                                      var rowData = jqGridPerson.getRowData(row);
                                      readOnlyForm(formPerson, rowData);
                                      $("#btnModalSubmit").prop('disabled', true);
                                       modalPerson.modal('toggle');
                                  } else {
                                      swal("Advertencia", "Por favor seleccione una fila.", "warning");
                                  }
                              },
                                  buttonicon: 'ui-icon-preview'
                          }
                     );
                     jqGridPerson.jqGrid('navButtonAdd', '#jqGridPersonPager', {
                         id: 'pager_save',
                         caption: '',
                         title: 'Agregar',
                         onClickButton: function (e) {
                             resetForm(formPerson);
                                 modalPerson.modal('toggle');
                         },
                         buttonicon: 'ui-icon-plus'
                     });
                     
                     jqGridPerson.jqGrid('navButtonAdd', '#jqGridPersonPager', {
                                 id: 'pager_edit',
                                 caption: '',
                                 title: 'Editar',
                                 onClickButton: function (e) {
                                      var row = jqGridPerson.jqGrid('getGridParam', 'selrow');
                                      if (row != null) {
                                         var rowData = jqGridPerson.getRowData(row);
                                         populateForm(formPerson, rowData);
                                         modalPerson.modal('toggle');
                                     } else {
                                         swal("Advertencia", "Por favor seleccione una fila.", "warning");
                                     }
                                 },
                                 buttonicon: 'ui-icon ui-icon-pencil'
                             });
                     
                     jqGridPerson.jqGrid('navButtonAdd', '#jqGridPersonPager', {
                         id: 'pager_delete',
                         caption: '',
                         title: 'Eliminar',
                         onClickButton: function (e) {
                             var row = jqGridPerson.jqGrid('getGridParam', 'selrow');
                             if (row != null) {
                                 var rowData = jqGridPerson.getRowData(row);
                                  populateForm(formPerson, rowData);
                                 deleterow();
                             } else {
                                 swal("Advertencia", "Por favor seleccione una fila.", "warning");
                             }
                         },
                         buttonicon: 'ui-icon ui-icon-trash'
                      });
                     jqGridPerson.jqGrid('navButtonAdd', '#jqGridPersonPager', {
                         id: 'pager_excel',
                         caption: '',
                         title: 'Export',
                         onClickButton: function (e) {
                              exportPersonGrid();
                         },
                         buttonicon: 'ui-icon-calculator'
                     });
                     
                      function exportPersonGrid() 
{                          $('#filters').val(jqGridPerson.jqGrid('getGridParam', 'postData').filters);
                          $('#jqGridPersonGridParameters').submit();
                                 }
                     
                      $(window).on('resize', function () {
                          var width = $('.jqgrid-responsive').width();
                          jqGridPerson.setGridWidth(width);
                      }).resize();
                     
                  });
               })(window, document, window.jQuery);
               </script>
                     
               <table id="jqGridPerson"></table>
               <table id="jqGridPersonPager"></table>
               
               <form method="post" id="jqGridPersonGridParameters" action="/springform/exportPerson"  target="_blank">
                  <input type="hidden" name="filters" id="filters" value=""/>
                  <input type="hidden" id="jqgridExport"/> 
               </form>
               

					</div>
                </div>
            </div>
            
	</div>
	  <!-- Modal-->
        <div id="modalPerson" tabindex="-1" role="dialog" aria-labelledby="myModalLabelLarge" aria-hidden="true" class="modal fade">
           <div class="modal-dialog modal-lg">
                <div class="modal-content">
  
                    <div class="modal-body">
                       <form method="POST" id="formPerson" action="${urlsavePerson}" data-parsley-validate="" novalidate="" class="form-horizontal">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">ID</label>
                                         <div class="col-lg-10">
										
                                         <jf:numberbox    precision="10"    id="idPerson" name="idPerson"  readonly="true"/>
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">CORREO</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="1" maxlength="255"   id="correo" name="correo" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">DIRECCION</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="2" maxlength="255"   id="direccion" name="direccion" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">ESPECIALIDAD</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="3" maxlength="100"   id="especialidad" name="especialidad" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">NOMBRE</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="5" maxlength="255"   id="nombre" name="nombre" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">TELEFONO</label>
                                         <div class="col-lg-10">

                                         <jf:textbox  tabindex="6" maxlength="255"   id="telefono" name="telefono" />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">DEPARTAMENTO</label>
                                         <div class="col-lg-10">

                                             <jf:combobox  url="${pageContext.request.contextPath}/cbofilterDepartamento"   id="catDepartamentoDelegate"  name="departamento.idDepartamento"  />
                                         </div>
                                     </div>
 
                                    <div class="form-group">
                                         <label class="col-sm-2 control-label">MUNICIPIO</label>
                                         <div class="col-lg-10">

                                             <jf:combobox  url="${pageContext.request.contextPath}/cbofilterMunicipio"   id="municipioDelegate"  name="municipio.idMunicipio"  />
                                         </div>
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