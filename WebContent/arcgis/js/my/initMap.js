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
			var _oilAndGasInfoTemplate = new InfoTemplate();
			  _oilAndGasInfoTemplate.setTitle("<b>Oil and Gas data</b>");
			
			var _oilAndGasInfoContent =
			    "<div class=\"demographicInfoContent\">" +
			"Gas production: ${PROD_GAS}<br>Oil production: ${PROD_OIL:formatNumber}" +
			"</div>";
			
			_oilAndGasInfoTemplate.setContent("${FIELD_NAME} production field<br>" +
			_oilAndGasInfoContent);
		      
			obs_insitu_collective_layer = new ArcGISDynamicMapServiceLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteocean_insitu_sfc_time/MapServer");
			obs_situ_geolink_met_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/3");
			obs_situ_geolink_oc_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/6");
			obs_situ_geolink_riv_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/9");
			obs_situ_geolink_wq_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/12");
			obs_situ_geolink_aq_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/15");
			obs_situ_geolink_radiosonde_layer = new FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/19");
			oilAndGasLayer = new ArcGISDynamicMapServiceLayer("http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Petroleum/KGS_OilGasFields_Kansas/MapServer", {
		        "id": "oilAndGasLayer",
		        "opacity": 0.75
		      });
		      oilAndGasLayer.setInfoTemplates({
		        0: { infoTemplate: _oilAndGasInfoTemplate }
		      });
		      
			vMap.addLayer(obs_insitu_collective_layer);	
			vMap.addLayer(obs_situ_geolink_met_layer);	
			vMap.addLayer(obs_situ_geolink_oc_layer);	
			vMap.addLayer(obs_situ_geolink_riv_layer);	
			vMap.addLayer(obs_situ_geolink_wq_layer);	
			vMap.addLayer(obs_situ_geolink_aq_layer);	
			vMap.addLayer(obs_situ_geolink_radiosonde_layer);	
			vMap.addLayer(oilAndGasLayer);
			
			obs_insitu_collective_layer.hide();				
			obs_situ_geolink_met_layer.hide();				
			obs_situ_geolink_oc_layer.hide();				
			obs_situ_geolink_riv_layer.hide();				
			obs_situ_geolink_wq_layer.hide();				
			obs_situ_geolink_aq_layer.hide();				
			obs_situ_geolink_radiosonde_layer.hide();
			oilAndGasLayer.hide();
			
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