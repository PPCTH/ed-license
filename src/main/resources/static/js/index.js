$(document).ready(function(){
	const idSearchURL = "search";
	const idSearchForm = $("#id-search-form");
	const changeFormBtn = $(".btn-change-search");
//	const idSearchBtn = $("#btn-submit-id-search")
	
	
	
	changeFormBtn.on("click",function() {
		let current = $(this).parents(".tab-pane");
		$(current).removeClass("active show")
				.siblings().tab("show");
	})
	
	idSearchForm.on("submit", function(){
		let form = this;
		let url = form.action + "/" + form.id.value;
		$.ajax({
			url: url,
			method: form.method,
			async: true,
			dataType : 'json',
			contentType: 'application/json',
			success: function (res) {
				renderTable(res);
            },
            error: function (data, textStatus, xhr) {
                console.log(data.responseText);
            }
		})
		return false;

	})
	
})


function renderTable(data){
	const dataTable = $("#result-id-table");
	
	dataTable.DataTable({
		dom: "<'table-responsive-md't",
		retrieve: true,
		paging: false,
		ordering: false,
		column:[
			{title: "Owner Name"},
			{title: "Bussiness Name"},
			{title: "Status"}
		]
	}).clear().rows.add(data).draw()
}