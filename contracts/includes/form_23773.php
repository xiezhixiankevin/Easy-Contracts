<?php	
	if (empty($_POST['name_26540_23773']) && strlen($_POST['name_26540_23773']) == 0 || empty($_POST['email_26540_23773']) && strlen($_POST['email_26540_23773']) == 0 || empty($_POST['email_26540_23773']) && strlen($_POST['email_26540_23773']) == 0 || empty($_POST['message_26540_23773']) && strlen($_POST['message_26540_23773']) == 0 || empty($_POST['message_26540_23773']) && strlen($_POST['message_26540_23773']) == 0)
	{
		return false;
	}
	
	$name_26540_23773 = $_POST['name_26540_23773'];
	$email_26540_23773 = $_POST['email_26540_23773'];
	$email_26540_23773 = $_POST['email_26540_23773'];
	$message_26540_23773 = $_POST['message_26540_23773'];
	$message_26540_23773 = $_POST['message_26540_23773'];
	
	$to = 'receiver@yoursite.com'; // Email submissions are sent to this email

	// Create email	
	$email_subject = "Message from a Blocs website.";
	$email_body = "You have received a new message. \n\n".
				  "Name_26540_23773: $name_26540_23773 \nEmail_26540_23773: $email_26540_23773 \nEmail_26540_23773: $email_26540_23773 \nMessage_26540_23773: $message_26540_23773 \nMessage_26540_23773: $message_26540_23773 \n";
	$headers = "MIME-Version: 1.0\r\nContent-type: text/plain; charset=UTF-8\r\n";	
	$headers .= "From: contact@yoursite.com\n";
	$headers .= "Reply-To: DoNotReply@yoursite.com";	
	
	mail($to,$email_subject,$email_body,$headers); // Post message
	return true;			
?>