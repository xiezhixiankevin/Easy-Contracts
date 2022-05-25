<?php	
	if (empty($_POST['name_38623_38612']) && strlen($_POST['name_38623_38612']) == 0 || empty($_POST['email_38623_38612']) && strlen($_POST['email_38623_38612']) == 0)
	{
		return false;
	}
	
	$name_38623_38612 = $_POST['name_38623_38612'];
	$email_38623_38612 = $_POST['email_38623_38612'];
	$input_1744_38612 = $_POST['input_1744_38612'];
	$input_563_38612 = $_POST['input_563_38612'];
	$input_563_38612 = $_POST['input_563_38612'];
	$optin_38623_38612 = $_POST['optin_38623_38612'];
	
	$to = 'receiver@yoursite.com'; // Email submissions are sent to this email

	// Create email	
	$email_subject = "Message from a Blocs website.";
	$email_body = "You have received a new message. \n\n".
				  "Name_38623_38612: $name_38623_38612 \nEmail_38623_38612: $email_38623_38612 \nInput_1744_38612: $input_1744_38612 \nInput_563_38612: $input_563_38612 \nInput_563_38612: $input_563_38612 \nOptin_38623_38612: $optin_38623_38612 \n";
	$headers = "MIME-Version: 1.0\r\nContent-type: text/plain; charset=UTF-8\r\n";	
	$headers .= "From: contact@yoursite.com\n";
	$headers .= "Reply-To: $email_38623_38612";	
	
	mail($to,$email_subject,$email_body,$headers); // Post message
	return true;			
?>