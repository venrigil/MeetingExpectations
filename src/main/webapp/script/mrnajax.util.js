
var ENTITY_MEETINGREPORT = 'wmeetingreport';


var init = function() {
	
	showmrTab(ENTITY_MEETINGREPORT);
	
	
		
	
};


var showmrTab = function(entity) {
	
	
	showmrHideCreate(entity, false);
	if (entity != HOME)
		$('#' + entity + '-search-reset').click();
};


var showmrHideCreate = function(entity, show) {
	
	if (show) {
	
	} else {
	
		 var formEleList = $('form#mr-search-form').serializeArray();
		 
		 var filterParam=new Array();
		 
		 for(var i=0;i<formEleList.length;i++){
			 filterParam[filterParam.length]=new param(formEleList[i].name,formEleList[i].value); 
			
				
		 }
	
		 
		 populatemrList(entity,filterParam);
		
			
	}
};


var param = function(name, value) {
	this.name = name;
	this.value = value;
};

var showMessage = function(message, entity) {
	$('#' + entity + '-show-message').show().html(
			'<p><b>' + message + '</b></p>');
};



var getData = function(url, filterData, successFn, errorFn) {
	
	$.ajax({
		url : url,
		type : "GET",
		data : filterData,
		success : function(resp) {
			
			if (successFn)
				successFn(resp);
		},
		error : function(e) {
			
			if (errorFn)
				errorFn(e);
		}
	});
	
	
	
	
		
};


var populatemrList = function(entity, filter) {
	
	
	
	
	var successFn = function(resp) {
		var data = '';
		
		if (resp) {
			
			data = resp.data;
			
		}
		
		
		var htmml = '';
		var htmagl = '';
		var htmdl = '';
		
		if (data.length > 0) {
			for ( var i = 0; i < data.length; i++) {
				
				htmml += '<tr>';
				htmagl += '<tr>';
				htmdl += '<tr>';
				
				alert(data.length);
					
		if (data[i].mdate != undefined) {htmml += '<td>' + data[i].mdate + '</td><td>'};
		if (data[i].mlocation != undefined)  {	htmml += data[i].mlocation + '</td><td>'};
		if (data[i].mplannedDuration != undefined)  {	htmml += data[i].mplannedDuration + '</td><td>'};
		if (data[i].mplannedStart != undefined) {	htmml += data[i].mplannedStart + '</td><td>'};
		if (data[i].mplannedEnd != undefined) { htmml += data[i].mplannedEnd + '</td><td>'};
		if (data[i].mdesc != undefined)  { htmml += data[i].mdesc +  '</td>;'};
				
				
		if (data[i].agimeetingID != undefined) {htmagl += '<td>' + data[i].agimeetingID + '</td><td>'};
		if (data[i].agititle != undefined) {htmagl += data[i].agititle + '</td>;';};
	
		if (data[i].pfirstName != undefined) {htmdl += '<td>' + data[i].pfirstName + '</td><td>'};			
		if (data[i].plastName != undefined) {htmdl += data[i].plastName + '</td><td>'};
		if (data[i].pemail != undefined) {htmdl += data[i].pemail + '</td>;'};
			}
		} else {
			
			var thElesLength = $('#' + entity + '-list-ctr table thead th').length;
			htmml += '<tr><td colspan="' + thElesLength
					+ '">No Meetings found</td></tr>';
			var thElesLengthagl = $('#wmragendaitem-list-ctr table thead th').length;
			htmagl += '<tr><td colspan="' + thElesLengthagl
					+ '">No Agenda Items found</td></tr>';
			
			var thElesLengthdl = $('#wmrdirectory-list-ctr table thead th').length;
			htmdl += '<tr><td colspan="' + thElesLengthdl
					+ '">No Participants found</td></tr>';
		}
		
		
		$('#'+entity+'-list-tbody').html(htmml);
		$('#wmragendaitem-list-tbody').html(htmagl);
		$('#wmrdirectory-list-tbody').html(htmdl);
	};
	
	getData("/"+entity,filter,successFn,null);
	
	
	
};