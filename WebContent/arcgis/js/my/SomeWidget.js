define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_OnDijitClickMixin",
    "dijit/_TemplatedMixin",
    "dojo/text!./templates/SomeWidget.html"
], function(declare, _WidgetBase, _OnDijitClickMixin, _TemplatedMixin, template) {

    return declare([_WidgetBase, _TemplatedMixin], {
        //set our template
    	templateString: template,
        //some properties
    	baseClass: "somWidget",
    	title:"", //we'll set this from the widget def
    	_counter: 1,
    	_global_var: _global_var++,
    	_firstClicked: false,
    	//define onClick handler
    	_onClick:function(){
    		if(this._firstClicked){
    			this.titleNode.innerHTML = this.title + " was clicked " + (++this._counter) + " times";
    		}else {
    			this.titleNode.innerHTML = this.title + " was clicked" + _global_var;
    			this._firstClicked = true;
    		}
    	},
    	
    	postCreate: function(){
    		this.titleNode.innerHTML = this.title;
    	}
    });

});