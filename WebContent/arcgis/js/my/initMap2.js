define([
        "esri/map",
        "esri/layers/ArcGISDynamicMapServiceLayer", 
        "esri/layers/FeatureLayer",
        "esri/InfoTemplate",
        "esri/TimeExtent",
        "esri/dijit/TimeSlider",
        "dojo/dom"
], function(
		Map,
		ArcGISDynamicMapServiceLayer, 
		FeatureLayer,
		InfoTemplate,
		TimeExtent,
		TimeSlider,
		dom){
	var vMap;
	
	return {
		init:function(){
			vMap = new Map("mapDiv", {
				basemap: "topo",
				center: [-46,37],				
				//infoWindow: popup			
			});	
			
			
			var timeSlider = new TimeSlider({
	            style: "width: 100%;"
	          }, dom.byId("timeSliderDiv"));
			vMap.setTimeSlider(timeSlider);
			 var timeExtent = new TimeExtent();
			currentDate = new Date();
	          timeExtent.startTime = new Date(currentDate.getTime() - (4*60*60*1000)); //4 hour before present
	          timeExtent.endTime = currentDate;
	          timeSlider.setThumbCount(1);
	          timeSlider.createTimeStopsByCount(timeExtent,5);
	          //timeSlider.createTimeStopsByTimeInterval(timeExtent, 2, "esriTimeUnitsYears");
	          //timeSlider.setThumbIndexes([0,1]);
	          //timeSlider.setThumbMovingRate(2000);
	          timeSlider.singleThumbAsTimeInstant(true);
	          //timeSlider.setTickCount(4);
	         
	          timeSlider.startup();
	          timeSlider.on("time-extent-change", function(evt) {
	              //var startValString = evt.startTime.getUTCFullYear();
	              //var endValString = evt.endTime.getUTCFullYear();
	              //dom.byId("daterange").innerHTML = "<i>" + startValString + " and " + endValString  + "<\/i>";
	            });
	            
		},
		getMap:function(){
			return vMap;
		}
	}
});