define([
        
],function(layer, legendPane
){
	if(layer.visible){ 
    	layer.hide();
    	esri.hide(legendpane);
    }
    else{
    	layer.show();
    	esri.show(legendpane);
    }  
});