<?php	
	if (empty($_POST['name_38623']) && strlen($_POST['name_38623']) == 0 || empty($_POST['email_38623']) && strlen($_POST['email_38623']) == 0)
	{
		return false;
	}
	
	$name_38623 = $_POST['name_38623'];
	$email_38623 = $_POST['email_38623'];
	
	$to = 'receiver@yoursite.com'; // Email submissions are sent to this email

	// Create email	
	$email_subject = "Message from a Blocs website.";
	$email_body = "You have received a new message. \n\n".
				  "Name_38623: $name_38623 \nEmail_38623: $email_38623 \n";
	$headers = "MIME-Version: 1.0\r\nContent-type: text/plain; charset=UTF-8\r\n";	
	$headers .= "From: contact@yoursite.com\n";
	$headers .= "Reply-To: DoNotReply@yoursite.com";	
	
	mail($to,$email_subject,$email_body,$headers); // Post message
	return true;			
?>