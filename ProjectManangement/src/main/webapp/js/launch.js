window.onload = LaunchWebseriver;
var status;

function waitAndstart(){
		status = 'start';		
		var hostname = "webauto";
		var request = "http://" + hostname + ":8080/robot/services/rest/start";
     $.ajax({
            url: request,
            data:"",
            type: "post",
            success: function (data) {
//             alert(s);
            	console.log(s);
            },
            error : function (e){

            //alert('failed');
	        console.log(e);
            },
         });
}
function LaunchWebseriver() {

 $(":submit").click(function(){
	
	 var url;
	 var user;
	 var pw;
	 var email;
	 
	 for (var i=0;i<$("input").length;i++){
		 
		 if ($("input").eq(i).attr("name") == "project_url"){
			 
			 url=$("input").eq(i).val();
		 }
		  if ($("input").eq(i).attr("name") == "project_username"){
			
			  user=$("input").eq(i).val()
		 }
		   if ($("input").eq(i).attr("name") == "project_password"){
			
			
			 pw=$("input").eq(i).val();
		 }
		  if ($("input").eq(i).attr("name") == "project_email"){
			email = $("input").eq(i).val();
		 }
	 }
	 if (url.length!=0&user.length!=0&pw.length!=0&email.length!=0)	{
	 
		 
		 
	for(var s=0;s<10;s++){
// 	setInterval(,1000);
	window.setTimeout("waitAndstart()",1000); 
   
	}
		 
	 }else {
		 
		 status = "stop";
		  //alert( status);
	 }
	 
	
   
});

    
}
