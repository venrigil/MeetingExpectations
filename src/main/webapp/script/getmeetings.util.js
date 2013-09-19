var ENTITY_MEETINGREPORT = 'getmeetings';
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
		 if(entity = "getmeetings"){
			 populateMeetingsList(entity,filterParam);	 
		 }
		 
		 	
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
var showMeetingsList = function(querystr){
	
	//-
	
	
	var filterParamML=new Array();
	for(var i=0; i<1; i++)  {
		
		
		filterParamML[filterParamML.length]=new param("query",querystr);
		}
	//-
	
		
	
	
	
	
	/*for(var i=0;i<1;i++){
		 filterParamML[i].name = query;
		 filterParamML[i].value = querystr;
	 }*/
	/*
	for(var i=0;i<filterParamML.length;i++){
		 alert(filterParamML[i].name);
		 alert(filterParamML[i].value);
	 }
	*/
	populatemCompleteList("meetingreport",filterParamML);
	
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
var populateMeetingsList = function(entity, filter) {
		var successFn=function(resp){
			var data='';
			if(resp){
				data=resp.mcreatedBy;
			}
			 
			 
			
			var selectBox= '';
				
				
			selectBox += '<option value="">Select</option>';
			if (data.length > 0) {
				for ( var i = 0; i < data.length; i++) {
					selectBox += '<option value="'+data[i].meetingID+'">'+data[i].mtitle+'</option>';
				};
				
			}
			$('#item-meetings-list').html(selectBox);
		
	};
	getData("/"+entity,filter,successFn,null);
	
	
	
};

var populatemCompleteList = function(entity, filter) {
	
	
	
	
	
	
		var successFn=function(resp){
			var data='';
			if(resp){
				
				data=resp.data;
			}
		
			
		
		
		
		var htmml = '';
		var htmagl = '';
		var htmdl = '';
		var htmacl = '';
		var htmmnl = '';
		var htmagnl = '';
		var htmacnl = '';
		var htmlmlcount = 0;
		
		if (data.length > 0) {
			for ( var i = 0; i < data.length; i++) {
				
				htmml += '<tr>'; htmagl += '<tr>'; 	htmdl += '<tr>'; htmacl += '<tr>';
				htmmnl += '<tr>'; htmagnl += '<tr>'; htmacnl += '<tr>';
				
				
					
				
				if (data[i].meetingID != undefined) {htmlmlcount++;};
				if (data[i].mdate != undefined) {htmml += '<td>' + data[i].mdate + '</td><td>';};
				if (data[i].mlocation != undefined)  {htmml += data[i].mlocation + '</td><td>';};
				if (data[i].mplannedDuration != undefined)  {htmml += data[i].mplannedDuration + '</td><td>';};
				if (data[i].mplannedStart != undefined) {htmml += data[i].mplannedStart + '</td><td>';};
				if (data[i].mplannedEnd != undefined) {htmml += data[i].mplannedEnd + '</td><td>';};
				if (data[i].mdesc != undefined)  {htmml += data[i].mdesc +  '</td>;';};
				
				
				
				

				if (data[i].acimeetingID != undefined) {htmacl += '<td>' + data[i].acimeetingID + '</td><td>';};
				if (data[i].acinotes != undefined) {htmacl += data[i].acinotes + '</td>;';};
				
			};
		}
		
		if (htmlmlcount == 0) {
			
			var thElesLength = $('#' + entity + '-list-ctr table thead th').length;
			htmml += '<tr><td colspan="' + thElesLength
					+ '">No Meetings found</td></tr>';
			
			
				
			
			
			
				
		}
		
		
		$('#w'+entity+'-list-tbody').html(htmml);
		$('#wmragendaitem-list-tbody').html(htmagl);
		
		$('#wmractionitem-list-tbody').html(htmacl);
		$('#wmrmnotes-list-tbody').html(htmmnl);
		var htmldl = '';
		var htmldlcount = 0;
		if (data.length > 0) {
			
			for ( var i = 0; i < data.length; i++) {
				
				
				htmldl += '<tr>';
				
				
				if (data[i].contactID != undefined) {htmldlcount++;};	
				if (data[i].pfirstName != undefined) {htmldl += '<td>' + data[i].pfirstName + '</td><td>';};			
				if (data[i].plastName != undefined) {htmldl += data[i].plastName + '</td><td>';};
				if (data[i].pemail != undefined) {htmldl += data[i].pemail + '</td>;';}
			};
		} 
		
		if (htmldlcount == 0) {
			var htmldlLength = $('#wmrdirectory-list-ctr table thead th').length;
			htmldl += '<tr><td colspan="' + htmldlLength + '">No Participants found</td></tr>';
		}
		
		$('#wmrdirectory-list-tbody').html(htmldl);

		
		var htmlmnl = '';
		var htmlmnlcount = 0;
		var mldlcount = 0;
		mldlcount = htmlmlcount + htmldlcount;
		if (data.length > 0) {
			var i = 0;
			for (i = mldlcount; i < data.length; i++) {
				
				
				if (data[i].agendaItemID) {break;};
				if (data[i].actionItemID) {break;};
				if (data[i].noteID != undefined) {htmlmnlcount++;};
				htmlmnl += '<tr>';
				if (data[i].ntitle != undefined) {htmlmnl += '<td>' + data[i].ntitle + '</td><td>';};
				if (data[i].ntext != undefined) {htmlmnl += data[i].ntext + '</td>;';};
			};
		} 
		
		if (htmlmnlcount == 0) {
			var htmlmnlLength = $('#wmrmnotes-list-ctr table thead th').length;
			htmlmnl += '<tr><td colspan="' + htmlmnlLength + '">No Meeting Notes found</td></tr>';
		}
		
		$('#wmrmnotes-list-tbody').html(htmlmnl);
		/***
		
		var htmlagl = '';
		var htmlaglcount = 0;
		var mdncount = 0;
		mdncount = htmlmlcount + htmldlcount + htmlmnlcount;
		if (data.length > 0) {
			var i = 0;
			for (i = mdncount; i < data.length; i++) {
				

				if (data[i].actionItemID) {break;};
				if (data[i].agendaItemID != undefined) {htmlaglcount++;};
				htmlagl += '<tr>';
				if (data[i].agimeetingID != undefined) {htmlagl += '<td>' + data[i].agimeetingID + '</td><td>';};
				if (data[i].agititle != undefined) {htmlagl += data[i].agititle + '</td>;';};
			};
		} 
		
		if (htmlaglcount == 0) {
			var htmlaglLength = $('#wmragendaitem-list-ctr table thead th').length;
			htmlagl += '<tr><td colspan="' + htmlaglLength + '">No Agenda Items found</td></tr>';
		}
		$('#wmragendaitem-list-tbody').html(htmlagl);
		***/
		
		
		var htmlagl = '';
		var htmlaglcount = 0;
		var mdncount = 0;
		var agnList = [];
		mdncount = htmlmlcount + htmldlcount + htmlmnlcount;
		if (data.length > 0) {
			var i = mdncount;
			for (i = mdncount; i < data.length; i++) {
				
				htmlagl += '<tr>';
				if (data[i].actionItemID) {break;};
				if (data[i].agendaItemID != undefined) {htmlaglcount++;};
				if (data[i].agimeetingID != undefined) {htmlagl += '<td>' + data[i].agimeetingID + '</td><td>';};
				if (data[i].agititle != undefined) {htmlagl += data[i].agititle + '</td><br></br>;';};
				
			
			for (var j = i+1; j < data.length; j++) {
				
				

				if (data[j].agendaItemID) {break;};
				if (data[j].actionItemID) {break;};
				if (data[j].noteID != undefined)
					{
					var tnoteID = data[j].noteID;
					
					if(agnList.indexOf(tnoteID) == -1)
					{
						htmlaglcount++;
						
						agnList.push(data[j].noteID);
						htmlagl += '<tr>';
						if (data[j].ntitle != undefined) {htmlagl += '<td> agendaitem-notes:' + data[j].ntitle + '</td><td>';};
						if (data[j].ntext != undefined) {htmlagl += data[j].ntext + '</td>;';};
						
					}
				}
			};
			
			}
		} 

		
		if (htmlaglcount == 0) {
			var htmlaglLength = $('#wmragendaitem-list-ctr table thead th').length;
			htmlagl += '<tr><td colspan="' + htmlaglLength + '">No Agenda Items found</td></tr>';
		}
		
		$('#wmragendaitem-list-tbody').html(htmlagl);

		
		
		var htmlacl = '';
		var htmlaclcount = 0;
		var mdnagcount = 0;
		mdnagcount = htmlmlcount + htmldlcount + htmlmnlcount + htmlaglcount;
		if (data.length > 0) {
			var i = 0;
			for (i = mdnagcount; i < data.length; i++) {
				

				if (data[i].actionItemID != undefined) {htmlaclcount++;};
				htmlacl += '<tr>';
				if (data[i].acimeetingID != undefined) {htmlacl += '<td>' + data[i].acimeetingID + '</td><td>';};
				if (data[i].acititle != undefined) {htmlacl += data[i].acititle + '</td>;';};
			};
		} 
		
		if (htmlaclcount == 0) {
			var htmlaclLength = $('#wmractionitem-list-ctr table thead th').length;
			htmlacl += '<tr><td colspan="' + htmlaclLength + '">No Action Items found</td></tr>';
		}
		$('#wmractionitem-list-tbody').html(htmlacl);
		
		
	};
	
	getData("/"+entity,filter,successFn,null);
	
	
	
};