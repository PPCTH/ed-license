nostra.onready = function () {
	const map = new nostra.maps.Map("map-container", {
		id: "mappp",
		logo: false,
		scalebar: false,
		basemap: "streetmap",
		slider: true,
		level: 15,
		lat: 13.7854946,
		lon: 100.54156739999999,
		country: "TH"
	});
	//navigator.geolocation.getCurrentPosition(initialMap)	
};

function initialMap(position){
	let currentLat = position.coords.latitude;
	let currentLon = position.coords.longitude;
	
	const map = new nostra.maps.Map("map-container", {
		id: "mappp",
		logo: false,
		scalebar: false,
		basemap: "streetmap",
		slider: true,
		level: 15,
		lat: currentLat,
		lon: currentLon,
		country: "TH"
	});

	const currentMarker = new nostra.maps.symbols.Marker({ 
							url: "", 
							width: 60, 
							height: 60, 
							attributes: { 
								POI_NAME: "Current", 
								POI_ROAD: "Current" 
								}, 
							//callout: nostraCallout 
							});
	const graphicLayer = new nostra.maps.layers.GraphicsLayer(map, { id: "gLayer" });
	
	map.addLayer(graphicLayer);
	graphicLayer.addMarker(currentLat, currentLon, currentMarker)
	
	
	map.events.click = function (evt) {
	//start your code here
		console.log("MAP ON CLICK")
	}
}