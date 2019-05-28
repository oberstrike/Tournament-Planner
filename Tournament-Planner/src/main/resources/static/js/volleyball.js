//document.getElementById('addPointA').addEventListener('click', event => {
//	document.getElementById('pointsA').setAttribute('text', document.getElementById('pointsA').getValue() + 1);
//	
//	
//});
//
//function addPointA(){
//	
//	var saveData = $.ajax({
//		type: 'POST',
//		url: "game?action=saveData",
//		data: game.addPointA(),
//		success: function(resultData) {alert("Daten wurden gesendet")}
//	});
//	saveData.error(function() {alert("Daten konnten nicht gespeichert werden");
//	});
//	
//}

$(document).ready(function{
	
	$("#cg").submit(function(event){
		
		event.preventDefault();
		
		fire_ajax_submit();
		
	});
	
});

function fire_ajax_submit(){
	var gameID = $("id").val();
	var optionID = $("optionID").val();
	var json = {
			"id" : gameID,
			"optionID" : optionID
	}
	
	$._GET({
		type: "POST",
		contentType: "application/json",
		url: "/change/Volleyball",
		data: JSON.stringify(json),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function(data){
			
		}
	
	});
}
