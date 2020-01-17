$(document).ready(function(){
	const idSearchURL = "search";
	const idSearchForm = $("#id-search-form");
	const changeFormBtn = $(".btn-change-search");
	$.fn.dataTable.ext.classes.sPageButton = 'page-item paginate_button blue lighten-2 wave-effect';
	
	/*Pace.on('hide', function(){
	      console.log('done');
	    });*/
	
	changeFormBtn.on("click",function() {
		let current = $(this).parents(".tab-pane");
		$(current).removeClass("active show")
				.siblings().tab("show");
	})
	
	idSearchForm.on("submit", function(){
		let form = this;
		let url = form.action + "/" + form.id.value;
		Pace.track(function(){
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
		})
		return false;
	})
	
	
	
})

function renderTable(data){
	const table = $("#result-id-table");
	const dataTable = table.DataTable({
		dom: "<'table-responsive-sm'tp",
		retrieve: true,
		ordering: false,
//		paging: false,
		pagingType: "numbers", 
		pageLength: 5,
		column:[
			{title: "Owner Name"},
			{title: "Bussiness Name"},
			{title: "Status"}
		],
	}).clear().rows.add(data.data).draw(true);
	
	
	table.find("tbody").on("click","tr",function(){
		var rowData = dataTable.row( this ).data();
		renderSearchModal(rowData[rowData.length-1])
	})
}

function renderSearchModal(bussiness_id){
	const bussinessDetailUrl = "search/bussiness_detail/" + bussiness_id;
	
	Pace.track(function(){
		$.ajax({
			url: bussinessDetailUrl,
			method: "GET",
			async: true,
			dataType : 'json',
			contentType: 'application/json',
			success: function (res) {
				renderModal(res);
            },
            error: function (data, textStatus, xhr) {
                console.log(data.responseText);
            }
		})
	})

}

function renderModal(data){
	let now = new Date();
	let endDate = new Date(data.bussiness_license_end);
	let statusColor = now.getTime() < endDate.getTime()? "#00c853": "#d50000"
	const animationCircle = $("#status-circle")
	const modal = $("#bussiness-info-wrp").modal({
		ready: function() { 
			animationCircle.show().css("background-color",statusColor).addClass("open");
	      },
	      complete: function(){
	    	animationCircle.removeClass("open");
	      }
	});
	const content = $("#bussiness-info-wrp .modal-content");

	$.each(data,function(i, v){
		if(i == "bussiness_license_start" ||
			i == "bussiness_license_end"){
			let date = new Date(v)
			v = date.toDateString()
		}
		content.find("." + i).html(v)
		
		
	})
	
	modal.modal("open");
}