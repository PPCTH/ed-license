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
	
	if(!$.isEmptyObject(data)){
		$.each(data, function(i, v){
			resultContainer.append(createCollection(i, v))
		})
		
		$(".detail").on("click", function(){
			let el = $(this).find(".bussiness-address");
			el.toggleClass("slide-in");
		})
	}else{
		resultContainer.append("<div class='center'>ไม่พบข้อมูลผู้ประกอบการ</div>");
	}
	
}

function createCollection(title, list){
	let listEl = "";
	$.each(list['bussiness_list'], function(i, v){
		
		let chip = v['bussiness_isAviable'] == "true"? 'light-green': 'red lighten-1';
		let startDate = v['bussiness_start_license'].split(',')[0];
		let endDate = v['bussiness_end_license'].split(',')[0];
		let el = "<a href='#!' data-id='" + v['bussiness_id'] + "' class='waves-effect  collection-item detail'>" +
					"<li>" + 
						"<div class='bussiness-info'>" +
							"<div class='row black-text'>" + 
								"<div class='col s12 m9 l9'>" + v['bussiness_name'] + "</div>" + 
								"<div class='col s12 m3 l3'>" + v['bussiness_id'] + "</div>" +
							"</div>" + 
							"<div class='row black-text mb-0'>" +
								"<div class='valign-wrapper col s12 m4 l5 grey-text'><span class='material-icons'>category</span>" + v['bussiness_type'] + "</div>" +
								"<div class='valign-wrapper col s12 m5 l4 grey-text'><span class='material-icons'>timelapse</span>" + startDate + " - " + endDate + "</div>" +
								"<div class='valign-wrapper col s12 m3 l3'><span class='chip white-text " + chip + "'>" + v['bussiness_status']+ "</span></div>" +
							"</div>" + 
						"</div>" +
						"<div class='bussiness-address black-text'><span class='material-icons grey-text'>keyboard_arrow_right</span>" +
							"<p> ที่ตั้งสำนักงาน </p>" +
							"<p>" + v['address'] + "</p>" +
						"</div>" +
					"</li>" +
				"</a>"
		listEl += el;
	})
	let headerEl = "<li class='collection-header blue darken-2 white-text'>" + 
						"<h4>" + list['owner_name'] + "</h4>" + 
						"<span class='grey-text text-lighten-4'>" + list['owner_id'] + "</span>" + 
					"</li>";
	let collectionEl = "<ul class='collection with-header'>" + headerEl + listEl + "</ul>";
	
	return collectionEl;	
}


function stickyHeaderSearch(el, elTop){
	if(window.pageYOffset > elTop){
		el.find(".card").addClass("sticky")
	}else{
		el.find(".card").removeClass("sticky")
	}
		
}