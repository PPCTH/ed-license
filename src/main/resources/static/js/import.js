$(document).ready(function(){
	
	$("#file-open").on("click", function(){$("#file-inp")[0].click()})
	$("#file-submit").on("click", function(){
		Pace.track(function(){
			let formData = new FormData();
			let fileInp = $("#file-inp")[0].files[0];
			formData.append('file', fileInp, fileInp.name)
			
			$.ajax({
				url: "/import/bussiness",
				method: 'POST',
				async: true,
				dataType : 'json',
				data: formData,
				enctype: 'multipart/form-data',
				processData: false,
		        contentType: false,
		        cache: false,
				success: function (res) {
					renderLogList(res)
	            },
	            error: function (data, textStatus, xhr) {
	                console.log(data);
	            }
			})
		})
	})
	
})

function renderLogList(list){
	const logEl = $("#log-wrp ul");
	$.each(list, function(i, v){
		console.log(v);
		logEl.append("<li>" + v + "</li>")
	})
}