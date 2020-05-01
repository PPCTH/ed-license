$(document).ready(function(){
	const mapUri = "http://localhost:8080/";
	const provEL = $("#province")
	const amphurEL = $("#amphur")
	const district = $("#district")
	

	$.getJSON(mapUri + "province", function(res){
		$.each(res,function(i, v){
			if( i == 22 ){
				let el = "<option value='" + i + "'>" + v + "</option>"
				provEL.append(el);
			}
			
		})
	})
	
	amphurEL.remoteChained({
	    parents : "#province",
	    url : mapUri + "amphur"
	});

	district.remoteChained({
	    parents : "#province, #amphur",
	    url : mapUri + "district"
	});
	
	
})