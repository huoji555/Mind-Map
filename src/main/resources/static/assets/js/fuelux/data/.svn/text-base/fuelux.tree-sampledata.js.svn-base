var DataSourceTree = function(options) {
	this._data 	= options.data;
	this._delay = options.delay;
}

DataSourceTree.prototype.data = function(options, callback) {
	var self = this;
	var $data = null;

	if(!("name" in options) && !("type" in options)){
		$data = this._data;//the root tree
		callback({ data: $data });
		return;
	}
	else if("type" in options && options.type == "folder") {
		if("additionalParameters" in options && "children" in options.additionalParameters)
			$data = options.additionalParameters.children;
		else $data = {}//no data
	}
	
	if($data != null)//this setTimeout is only for mimicking some random delay
		setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);


};


var tree_data = {
	
	'vehicles' : {name: 'Vehicles', type: 'folder'}	
	
}

tree_data['vehicles']['additionalParameters'] = {
	'children' : {
	
		'boats' : {name: 'Boats', type: 'item'}
	}
}




var treeDataSource = new DataSourceTree({data: tree_data});

alert(tree_data);




