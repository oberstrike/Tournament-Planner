document.getElementById('addPointA').addEventListener('click', event => {
	document.getElementById('pointsA').setAttribute('text', document.getElementById('pointsA').getValue() + 1);
	
	
});

function addPointA(){
	
	var saveData = $.ajax({
		type: 'POST',
		url: "game?action=saveData",
		data: game.addPointA(),
		success: function(resultData) {alert("Daten wurden gesendet")}
	});
	saveData.error(function() {alert("Daten konnten nicht gespeichert werden");
	});
	
}

