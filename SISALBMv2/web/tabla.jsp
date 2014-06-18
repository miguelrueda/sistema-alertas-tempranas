<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="resources/css/jquery-ui-1.10.4.custom.css" type="text/css" rel="stylesheet" />
        <link href="resources/css/basic/jtable_basic.css" type="text/css" rel="stylesheet" />
        <!--<script src="resources/js/jquery-2.1.1.js" type="text/javascript"></script>-->
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="resources/js/jquery-ui-1.10.4.custom.js" type="text/javascript"></script>
        <script src="resources/js/jquery.jtable.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#TableContainer").jtable({
                    title: 'Vulnerabilidades Más Recientes',
                    actions: {
                        listAction: '/CRUDController?action=list',
                        createAction: 'CRUDController?action=create',
                        updateAction: 'CRUDController?=action=update',
                        deleteAction: 'CRUDController?action=delete'
                    },
                    fields: {
                        name: {
                            key: true,
                            title: 'Nombre',
                            list: true,
                        },
                        severity: {
                            title: 'Criticidad',
                            width: '30%'
                        },
                        description: {
                            title: 'Descripción',
                            width: '60%'
                        }
                    }
                });
                $('#TableContainer').jtable('load');
            });
        </script>
    </head>
    <body>
        <div style="width: 60%; margin-right: 20%; margin-left: 20%; text-align: center">
            <h1>Load Data jsp servlet</h1>
            <div id="TableContainer"></div>
        </div>
    </body>
</html>