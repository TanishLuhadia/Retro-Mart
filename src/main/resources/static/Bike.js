// function to populate models based on selected brand
function showModels() {
	var brandSelect = document.getElementById("brand");
	var modelsSelect = document.getElementById("model");
	var modelsLabel = document.getElementById("models-label");
	modelsSelect.innerHTML = "";
	var selectedBrand = brandSelect.value;
	
	var models = [];
	switch (selectedBrand) {
		case "TVS":
			models = ["Select", "Apache RTR 160", "Apache RTR 180", "Apache RTR 200 4V", "Apache RR 310", "Star City Plus", "Jupiter", "Ntorq 125", "Sport"];
			break;
		case "Hero":
			models = ["Select", "Hero Splendor",
            "Hero Passion",
            "Hero HF Deluxe",
            "Hero Glamour",
            "Hero Super Splendor",
            "Hero Xtreme",
            "Hero Xpulse",];
			break;
		case "Bajaj":
			models = ["Select","Pulsar", "Dominar", "Avenger", "Platina", "CT 100", "Chetak" ];
			break;
		case "Honda":
			models = ["Select", "CBR1000RR", "CBR650R", "CBR500R", "CBR300R", "CBR250RR", "CB1100EX", "CB500F", "CB300R", "CB125R", "CRF1100L Africa Twin", "CRF300L", "CRF250 Rally", "CRF150L", "Super Cub C125", "ADV150"];
			break;
		default:
			break;
    }
	
	for (var i = 0; i < models.length; i++) {
		var option = document.createElement("option");
		option.text = models[i];
		modelsSelect.add(option);
    }

	
	modelsSelect.style.display = "block";
	modelsLabel.style.display = "block";
}

// function to populate varients based on selected model
function showVariants(){
    const modelSelect = document.getElementById("models");
   
    variantSelect.innerHTML = "";
    var selectedModel = modelSelect.value;

}

  

// Function to handle selection of fuel type
function selectFuel(event, fuelType) {
    // Get all fuel buttons
    var fuelButtons = document.querySelectorAll('#main .fuel button');
    
    // Deselect all fuel buttons
    fuelButtons.forEach(function(button) {
        button.classList.remove('selected');
    });

    // Select the clicked fuel button
    event.target.classList.add('selected');

    // Set the selected fuel type value to some hidden input field or handle it as needed
    document.getElementById('fuel').value = fuelType;
}




// Function to handle selection of Owners type
function selectOwners(event, ownerType) {
    // Get all owner buttons
    var ownerButtons = document.querySelectorAll('#main #owners-buttons button');
    
    // Deselect all owner buttons
    ownerButtons.forEach(function(button) {
        button.classList.remove('selected');
    });

    // Select the clicked owner button
    event.target.classList.add('selected');

    // Set the selected owner type value to some hidden input field or handle it as needed
    document.getElementById('numberOfOwner').value = ownerType;
}

 // Validation for Year
 document.getElementById("year").addEventListener("input", function() {
    var year = this.value;
    if (year < 1900) {
        document.getElementById("year-error").textContent = "Year must be at least 1900";
        this.value = 1900;
    } else {
        document.getElementById("year-error").textContent = "";
    }
});

 // Validation for KM Driven
 document.getElementById("driven").addEventListener("input", function() {
    var kmDriven = this.value;
    if (kmDriven.length > 6) {
        document.getElementById("km-error").textContent = "Maximum 6 digits allowed";
        this.value = kmDriven.slice(0, 6);
    } else {
        document.getElementById("km-error").textContent = "";
    }
});

// Validation for Title
document.getElementById("conditions").addEventListener("input", function() {
    var title = this.value;
    if (title.length > 70) {
        document.getElementById("title-error").textContent = "Maximum 70 characters allowed";
        this.value = title.slice(0, 70);
    } else {
        document.getElementById("title-error").textContent = "";
    }
});

// Validation for Description
document.getElementById("features").addEventListener("input", function() {
    var description = this.value;
    if (description.length > 5000) {
        document.getElementById("description-error").textContent = "Maximum 5000 characters allowed";
        this.value = description.slice(0, 5000);
    } else {
        document.getElementById("description-error").textContent = "";
    }
});