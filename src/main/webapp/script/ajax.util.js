var HOME = 'home';
var ENTITY_DIRECTORY = 'wdirectory';
var ENTITY_MEETING = 'wmeeting';
var ENTITY_AGENDAITEM = 'wagendaitem';
var ENTITY_ACTIONITEM = 'wactionitem';


var init = function() {
	
	showTab(HOME);
	
	$('#tabs a').click(function(event) {
		showTab(event.currentTarget.id);
	});
}


var showTab = function(entity) {
	
	$('.tab').removeClass("active");
	
	$('#' + entity).addClass("active");
	
	$('.g-unit').hide();
	
	$('#' + entity + '-tab').show();
	
	$('.message').hide();
	
	showHideCreate(entity, false);
	if (entity != HOME)
		$('#' + entity + '-search-reset').click();
}


var showHideCreate = function(entity, show) {
	
	if (show) {
		
		$('#' + entity + '-search-ctr').hide();
		
		$('#' + entity + '-list-ctr').hide();
		
		$('#' + entity + '-create-ctr').show();

	} else {
		
		$('#' + entity + '-search-ctr').show();
		
		$('#' + entity + '-list-ctr').show();
		
		$('#' + entity + '-create-ctr').hide();
		
		
		if (entity != HOME)
			
			populateList(entity, null);
	}
}


var param = function(name, value) {
	this.name = name;
	this.value = value;
}


var add = function(entity) {
	$('.message').hide();
	$('#' + entity + '-reset').click();
	
	showHideCreate(entity, true);
	/*
	$("span.readonly input").attr('readonly', false);
	$("select[id$=actionitem-directory-list] > option").remove();
	$("select[id$=actionitem-agendaitem-list] > option").remove();
	$("select[id$=agendaitem-meeting-list] > option").remove();
	
	if (entity == ENTITY_AGENDAITEM) {
		
		populateSelectBox('agendaitem-meeting-list', '/wmeeting');
	} else if (entity == ENTITY_ACTIONITEM) {
		
		
		populateSelectBox('actionitem-directory-list', '/wdirectory');
		populateSelectBox('actionitem-agendaitem-list', '/wagendaitem');
	}
	*/
	populateSelectBox('wdirectory-list', '/wdirectory');
}


var search = function(entity) {
	$('.message').hide();
	
	var formEleList = $('form#' + entity + '-search-form').serializeArray();
	
	var filterParam = new Array();
	for ( var i = 0; i < formEleList.length; i++) {
		filterParam[filterParam.length] = new param(formEleList[i].name,
				formEleList[i].value);
	}
	
	populateList(entity, filterParam);
}

var showMessage = function(message, entity) {
	$('#' + entity + '-show-message').show().html(
			'<p><b>' + message + '</b></p>');
}

var formValidate = function(entity) {
	var key;
	var formEleList = $('form#' + entity + '-create-form').serializeArray();
	key = formEleList[0].value;
	switch (entity) {
	case ENTITY_AGENDAITEM:
		var valueProduct = $('#agendaitem-meeting-list').val();
		if (valueProduct == "" || key == "") {
			showMessage(
					'please check the AgendaItem and Meeting entries/selections in the form',
					entity);
			return;
		}
		break;
	case ENTITY_ACTIONITEM:
		var valueCustomer = $('#actionitem-directory-list').val();
		var valueItem = $('#actionitem-agendaitem-list').val();
		if (valueCustomer == "" || valueItem == "") {
			showMessage(
					'please check the Paricipant and AgendaItem entries/selections in the form',
					entity);
			return;
		}
		break;
	case ENTITY_DIRECTORY:
		var hasError = false;
		/*
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddressVal = $("#eMail").val();
		if (emailaddressVal == '' || !emailReg.test(emailaddressVal)
				|| key == "") {
			hasError = true;
		}
		if (hasError == true) {
			showMessage('please check the name and email entries in the form',
					entity);
			return;
		}
		*/
		break;
		
	default:
		if (key == "") {
			showMessage('please check the entries in the form', entity);
			return;
		}
		break;
	}
	save(entity);
	$('#' + entity + '-show-message').hide();
}


var save = function(entity) {
	
	var data = new Array();
	
	var formEleList = $('form#' + entity + '-create-form').serializeArray();
	for ( var i = 0; i < formEleList.length; i++) {
		data[data.length] = new param(formEleList[i].name, formEleList[i].value);
	}
	
	data[data.length] = new param('action', 'PUT');
	
	$.ajax({
		url : "/" + entity,
		type : "POST",
		data : data,
		success : function(data) {
			showHideCreate(entity, false);
		}
	});
	$('#' + entity + '-reset').click();
}


var edit = function(entity, id) {
	var parameter = new Array();
	parameter[parameter.length] = new param('q', id);
	$.ajax({
		url : "/" + entity,
		type : "GET",
		data : parameter,
		success : function(resp) {
			var data = resp.data[0];
			var formElements = $('form#' + entity + '-create-form :input');
			for ( var i = 0; i < formElements.length; i++) {
				if (formElements[i].type != "button") {
					var ele = $(formElements[i]);
					if (ele.attr('name') == "meeting") {
						$("select[id$=agendaitem-meeting-list] > option")
								.remove();
						ele.append('<option value="'
								+ eval('data.' + ele.attr('name')) + '">'
								+ eval('data.' + ele.attr('name'))
								+ '</option>');
					} else
						ele.val(eval('data.' + ele.attr('name')));
				}
			}
			showHideCreate(entity, true);
			$("span.readonly input").attr('readonly', true);
		}
	});
}


var cancel = function(entity) {
	$('.message').hide();
	
	showHideCreate(entity, false);
}


var deleteEntity = function(entity, id, parentid) {
	var parameter = new Array();
	parameter[parameter.length] = new param('id', id);
	parameter[parameter.length] = new param('parentid', parentid);
	parameter[parameter.length] = new param('action', 'DELETE');
	
	$.ajax({
		url : "/" + entity,
		type : "POST",
		data : parameter,
		dataType : "html",
		success : function(resp) {
			showHideCreate(entity, false);
			if (resp != '') {
				showMessage(resp, entity);
			}

		},
		error : function(resp) {
			showMessage(resp, entity);
		}
	});
}



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
}



var populateSelectBox = function(id, url) {
	
	
	var successFn = function(resp) {
		
		var selectBox = $('#' + id);
		
		selectBox.innerHTML = '';
		
		var data = resp.data;
		
		selectBox.append('<option value="">Select</option>');
		
		for ( var i = 0; i < data.length; i++) {
			selectBox.append('<option value="' + data[i].name + '">'
					+ data[i].name + '</option>');
		}
	};
	
	getData(url, null, successFn, null);
}


var populateList = function(entity, filter) {
	
	
	
	var successFn = function(resp) {
		var data = '';
		
		if (resp) {
			
			data = resp.data;
		}
		
		var htm = '';
		
		if (data.length > 0) {
			for ( var i = 0; i < data.length; i++) {
				
				htm += '<tr>';
				switch (entity) {
				
				case ENTITY_MEETING:
			
					htm += '<td>' + data[i].id + '</td><td>'
							+ data[i].actualDuration + '</td><td>'
							+ data[i].agendaItemsString + '</td><td>'
							+ data[i].createdBy + '</td><td>'
							+ data[i].createdOn + '</td><td>'
							+ data[i].date + '</td><td>'
							+ data[i].deleted + '</td><td>'
							+ data[i].description + '</td><td>'
							+ data[i].location + '</td><td>'
							+ data[i].meetingID + '</td><td>'
							+ data[i].modifiedOn + '</td><td>'
							+ data[i].participantsString + '</td><td>'
							+ data[i].plannedDuration + '</td><td>'
							+ data[i].plannedEnd + '</td><td>'
							+ data[i].plannedStart + '</td><td>'
							+ data[i].title + '</td><td>'
							+ data[i].url + '</td>';
					break;
				case ENTITY_AGENDAITEM:
					htm += '<td>' + data[i].AgendaitemID + '</td><td>' + data[i].title
							+ '</td><td>' + data[i].meetingID + '</td>';
					break;
				case ENTITY_ACTIONITEM:
					htm += '<td>' + data[i].name + '</td><td>'
							+ data[i].agendaitemName + '</td><td>'
							+ data[i].directoryName + '</td><td>'
							+ data[i].shipTo + ',' + data[i].city + ','
							+ data[i].state + '-' + data[i].zip + '</td><td>'
							+ data[i].quantity + '</td><td>' + data[i].price
							+ '</td>';
					break;
				case ENTITY_DIRECTORY:
					htm += '<td>' + data[i].contactID + '</td><td>'
							+ data[i].createdOn + '</td><td>'
							+ data[i].deleted + '</td><td>'
							+ data[i].email + '</td><td>'
							+ data[i].firstName + '</td><td>'
							+ data[i].lastName + '</td><td>'
							+ data[i].modifiedOn + '</td>';
					break;
				default:
					htm += "";
				}
				if (entity != ENTITY_ACTIONITEM)
					htm += '<td><a href="#" class="delete-entity" onclick=\'deleteEntity("'
							+ entity
							+ '","'
							+ data[i].name
							+ '",null)\'>Delete</a> | <a href="#" class="edit-entity" onclick=\'edit("'
							+ entity
							+ '","'
							+ data[i].name
							+ '")\'>Edit</a></td></tr>';
				else
					htm += '<td><a href="#" class="delete-entity" onclick=\'deleteEntity("'
							+ entity
							+ '","'
							+ data[i].name
							+ '","'
							+ data[i].directoryName
							+ '")\'>Delete</a></td></tr>';
			}
		} else {
			
			var thElesLength = $('#' + entity + '-list-ctr table thead th').length;
			htm += '<tr><td colspan="' + thElesLength
					+ '">No agendaitems found</td></tr>';
		}
		$('#'+entity+'-list-tbody').html(htm);
	}
	getData("/"+entity,filter,successFn,null);
}