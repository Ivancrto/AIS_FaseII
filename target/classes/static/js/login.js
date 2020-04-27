function addUser() { // Función encargada de almacenar en la "BD" la
						// información nueva.

	var formData = {};
	var username = $('#usernameR').val();
	formData['nameUser'] = $('#usernameR').val();
	formData['password'] = $('#passwordR').val();
	var b = true;

	$.ajax({
		url : "http://localhost:8080/user/search/findByNameUser?nameUser="
				+ username,
		success : function(data) {

			$("#myModal .modal-body").append(
					"<p>This user is already registered.</p>");
		},
		error : function() {
			$.ajax({
				type : "POST",
				url : 'http://localhost:8080/user/',
				dataType : 'json',
				async : true,
				// El objeto hay que convertirlo a texto
				data : JSON.stringify(formData),
				contentType : 'application/json'
			});
			location.href = "menuprincipal?nameuser=" + username;
		}
	});

}

function iniciarUser() {

	var username = $('#username').val();
	var passwordUser = $('#password').val();
	$
			.ajax(
					{
						url : "http://localhost:8080/user/search/findByNameUser?nameUser="
								+ username
					}).then(function(data) {
				if (passwordUser == data.password) {
					location.href = "menuprincipal?nameuser=" + username;
				} else {
					alert("Wrong username or password");
				}

			});

}