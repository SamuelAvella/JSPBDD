<%-- index.jsp (proyecto Incrementa5) --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="connectionpool.ConnectionPool"%>
<%@page import="individuos.IndividuoService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="individuos.Individuo"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="assets/css/style.css">

</head>
</head>
<body>
    <%
      //Usuario de la base de datos
     String user = "juan";
    //Contraseña de la base de datos
    String password = "12345678";
    //Pool de conexiones a la base de datos
    ConnectionPool pool = new ConnectionPool("jdbc:mysql://localhost:3306/personify", user, password);
    IndividuoService individuosSvc = new IndividuoService(pool.getConnection());
    ArrayList<Individuo> list = individuosSvc.requestAll("");
    for(int i = 0; i < list.size(); i++){
        out.print(String.format("<p>%s</p>", list.get(i).getNombre()));
    }

    %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>