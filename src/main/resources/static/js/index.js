$(document).ready(function(){
	const idSearchURL = "search";
	const idSearchForm = $("#id-search-form");
	const changeFormBtn = $(".btn-change-search")
	
	
	
	changeFormBtn.on("click",function() {
		let current = $(this).parents(".tab-pane");
		$(current).removeClass("active show")
				.siblings().tab("show");
	})
	
	idSearchForm.on("submit", function(){
		let form = this;
				
		$.ajax({
			url: form.action,
			method: form.method,
			dataType : 'json',
			data: {
				id : form.id.value
			},
			success: function(res){
				console.log("------------res---------------")
				console.log(res)
			}
		})
		return false;
	})
})
