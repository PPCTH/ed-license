$(document).ready(function(){
	const idSearchForm = $("#id-search-form");
	const changeFormBtn = $(".btn-change-search");
	
	
	idSearchForm.find(".card i").on("click", function(){
		let value = idSearchForm.find(".card input").val();
		let url = "search/" + value;
		Pace.track(function(){
			$.ajax({
				url: url,
				method: 'POST',
				async: true,
				dataType : 'json',
				contentType: 'application/json',
				success: function (res) {
					renderResult(res);
	            },
	            error: function (data, textStatus, xhr) {
	                console.log(data.responseText);
	            }
			})
		})
		return false;
	})
	
	const inpOffsetTop = idSearchForm.find("input").offset().top;
	window.onscroll = function(){stickyHeaderSearch(idSearchForm, inpOffsetTop)};
})

function renderResult(data){
	const resultContainer = $("#result-wrp");
	resultContainer.empty();
	
	$.each(data, function(i, v){
		resultContainer.append(createCollection(i, v))
	})
	
	$(".detail").on("click", function(){
		let bussinessId = $(this).data("id")
		renderSearchModal(bussinessId)
	})
}

function createCollection(title, list){
	let listEl = "";
	$.each(list,function(i, v){
		let wave = v['bussiness_isAviable'] == "true"? 'waves-green': 'waves-red';
		let el = "<a href='#!' data-id='" + v['bussiness_id'] + "' class='waves-effect " + wave + " collection-item detail'>" +
					"<li>" + 
						"<span class='title black-text'>" + v['bussiness_name'] + "</span>" + 
						"<p class='grey-text mb-0'>" + v['bussiness_status'] + "</p>" +
					"</li>" +
				"</a>"
		listEl += el;
	})
	let headerEl = "<li class='collection-header blue darken-2 white-text'><h4>" + title + "</h4></li>";
	let collectionEl = "<ul class='collection with-header'>" + headerEl + listEl + "</ul>";
	
	return collectionEl;	
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

function stickyHeaderSearch(el, elTop){
	if(window.pageYOffset > elTop){
		el.find(".card").addClass("sticky")
	}else{
		el.find(".card").removeClass("sticky")
	}
		
}