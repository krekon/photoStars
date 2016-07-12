<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayPal with Spring MVC</title>
</head>
<body>

<form method="post" action="${initParam['posturl']}">

<input type="hidden" name="upload" value="1" />
<input type="hidden" name="return" value="${initParam['returnurl']}" />
<input type="hidden" name="cmd" value="_cart" />
<input type="hidden" name="business" value="${initParam['business']}" />


<!-- Product 1 -->

<input type="hidden" name="item_name_1" value="Product 1" />
<input type="hidden" name="item_number_1" value="p1" />
<input type="hidden" name="amount_1" value="2" />
<input type="hidden" name="quantity_1" value="3" />

<input type="image" src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif">

</form>

</body>
</html>