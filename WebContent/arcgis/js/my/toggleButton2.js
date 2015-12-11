define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dijit/_WidgetBase",   
    "dijit/_TemplatedMixin", 
    "dijit/_WidgetsInTemplateMixin",
    "dojo/text!./templates/toggleButton.html",
    "dijit/form/ToggleButton",
    "esri/layers/ArcGISDynamicMapServiceLayer",
    "esri/layers/layer"
    
], function(declare, lang, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, template) {

    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        //set our template
    	templateString: template,
        //some properties
    	baseClass: "dijitToggleButton",
    	title:"", //we'll set this from the widget def 
    	
    	name: "unknown",
    	map:map,
    	
    	layer: new esri.layers.ArcGISDynamicMapServiceLayer("http://nowcoast.noaa.gov/arcgis/rest/services/nowcoast/obs_meteocean_insitu_sfc_time/MapServer"),
    	
    	legenPane:"",    	
    	
    	//public method onChange
    	onChange:function(){  		    		
    		this._onChange();
    	},   	
    	//private method _onChange
    	_onChange: function(checked){
    		if(!this.checked){
    			map.addLayer(this.layer);
    		//this.layer.show();
    		//this.toggleBtn.set("label", " changed");	  
    		}else{
    			map.removeLayer(this.layer);
    		}
    		
    		
    		
    	},
    	
    	//the last change to set up before widget fully serve in web page
    	postCreate: function(){
    		this.toggleBtn.innerHTML = this.title;
    		this.toggleBtn.set("label", this.title);
    	}
    });

});