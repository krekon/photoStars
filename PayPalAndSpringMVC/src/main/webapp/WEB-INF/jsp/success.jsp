<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayPal with Spring MVC</title>
</head>
<body>
<p>Order Information</p>

<p>Transaction ID:</p>${result.txn_id }
<p>First Name:</p>${result.first_name }
<p>Last Name:</p>${result.last_name }
<p>Email:</p>${result.payer_email }
<p>Payment Gross:</p>${result.payment_gross }
<p>Payment Fee:</p>${result.payment_fee }


</body>
</html>