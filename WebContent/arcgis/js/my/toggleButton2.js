define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dijit/_WidgetBase",   
    "dijit/_TemplatedMixin", 
    "dijit/_WidgetsInTemplateMixin",
    "dojo/text!./templates/toggleButton2.html",
    "dijit/form/ToggleButton",
    "esri/layers/ArcGISDynamicMapServiceLayer",
    "esri/layers/FeatureLayer",
    "esri/layers/layer",
    "esri/InfoTemplate"
    
], function(declare, lang, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, template) {

    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        //set our template
    	templateString: template,
        //some properties
    	baseClass: "dijitToggleButton",
    	//title:"", //we'll set this from the widget def 
    	
    	name: "unknown",
    	map:map,
    	layer:null,    	
    	//layer: new esri.layers.ArcGISDynamicMapServiceLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteocean_insitu_sfc_time/MapServer"),
    	
    	legenPane:"",    	
    	
    	//public method onChange
    	onChange:function(){  		    		
    		this._onChange();
    	},   	
    	//private method _onChange
    	_onChange: function(){    		
    		
    		checked = this.toggleBtn.get("checked");
    		if(this.id === "obs_insitu_collective"){
    			if(checked){
        	    	this.layer = new esri.layers.ArcGISDynamicMapServiceLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteocean_insitu_sfc_time/MapServer");
        			map.addLayer(this.layer); 		
        			esri.show(dojo.byId("obs_pane"));
        		}else{
        			map.removeLayer(this.layer);
        			esri.hide(dojo.byId("obs_pane"));
        		}
    	    }
    		else if(this.id==="obs_situ_geolink_met"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/3");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}  
    		else if(this.id==="obs_situ_geolink_oc"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/6");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}
    		else if(this.id==="obs_situ_geolink_riv"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/9");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}
    		else if(this.id==="obs_situ_geolink_wq"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/12");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}
    		else if(this.id==="obs_situ_geolink_aq"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/15");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}
    		else if(this.id==="obs_situ_geolink_radiosonde"){
    			if(checked){
        			this.layer = new esri.layers.FeatureLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteoceanhydro_insitu_pts_geolinks/MapServer/19");
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}
    		else if(this.id==="oil_data"){
    			if(checked){
    				var _oilAndGasInfoTemplate = new esri.InfoTemplate();
    				  _oilAndGasInfoTemplate.setTitle("<b>Oil and Gas data</b>");
    				
    				var _oilAndGasInfoContent =
    				    "<div class=\"demographicInfoContent\">" +
    				"Gas production: ${PROD_GAS}<br>Oil production: ${PROD_OIL:formatNumber}" +
    				"</div>";
    				
    				_oilAndGasInfoTemplate.setContent("${FIELD_NAME} production field<br>" +
    				_oilAndGasInfoContent);
        			this.layer = new esri.layers.ArcGISDynamicMapServiceLayer("http://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Petroleum/KGS_OilGasFields_Kansas/MapServer", {
        		        "id": "oilAndGasLayer",
        		        "opacity": 0.75
        		      });
        		      this.layer.setInfoTemplates({
        		        0: { infoTemplate: _oilAndGasInfoTemplate }
        		      });
        			map.addLayer(this.layer); 		        		
        		}else{
        			map.removeLayer(this.layer);
        		}
    		}   		
    		
    	},
    	
    	//the last change to set up before widget fully serve in web page
    	postCreate: function(){
    		this.toggleBtn.innerHTML = this.title;
    		this.toggleBtn.set("label", this.title);
    	}
    });

});