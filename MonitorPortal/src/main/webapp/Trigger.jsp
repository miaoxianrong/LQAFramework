<html>

<head>
</head>
<script src="./js/jquery.js"></script>
<body>  
Welcome -- Trigger
<script>
    	function test(){
		$.ajax({
			url: "http://155.35.88.222/robot/services/rest/start",
			data:"",
			type: "post",
			success: function (data) {
				console.log(JSON.stringify(data));
			},
		});
        }
    	test();
</script>
</body>
</html>