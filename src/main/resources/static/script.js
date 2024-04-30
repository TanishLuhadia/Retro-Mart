// function to populate models based on selected brand

function showModels() {
	var brandSelect = document.getElementById("brand");
	var modelsSelect = document.getElementById("model");
	var modelsLabel = document.getElementById("models-label");
	modelsSelect.innerHTML = "";
	var selectedBrand = brandSelect.value;
	
	var models = [];
	switch (selectedBrand) {
		case "Maruti Suzuki":
			models = ["Select", "Alto 800", "Alto K10", "Ritz"];
			break;
		case "Hyundai":
			models = ["Select", "Aura", "Creta", "New Verna"];
			break;
		case "Tata":
			models = ["Select", "Tiago", "Nano", "Punch", "Bolt"];
			break;
		case "Honda":
			models = ["Select", "Elevate", "Amaze", "City"];
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
    const modelSelect = document.getElementById("model");
    const variantSelect = document.getElementById("varient"); // Corrected ID to "variants"
    const variantLabel = document.getElementById("variants-label");
    variantSelect.innerHTML = "";
    var selectedModel = modelSelect.value;

    var variants = [];
    switch (selectedModel) {
        case "Alto 800":
            variants = ["Select", "VXI Plus Option", "0.8 STD CNG", "0.8 VXI (O)"];
            break;
        case "Alto K10":
            variants = ["Select", "2010-2014 1.0 VXI (O)", "VXI Explore Limited Edition", "LXI Music Edition"];
            break;
        case "Ritz":
            variants = ["Select", "1234", "5678", "9999"];
            break;
        case "Honda":
            variants = ["Select", "0000", "3333", "8888", "#1#1"];
            break;
        case "Bolt":
            variants = ["Select", "Tata Bolt Revotron XE "," Tata Bolt Revotron XM","Tata Bolt Revotron XMS"," Tata Bolt Revotron XT ","Tata Bolt Quadrajet XT "];
            break;
        case "Tiago":
            variants = ["Select", "XE","XT (O)","XM","XE CNG"];
            break;
        case "Punch":
            variants = ["Select", "Pure MT","Pure Rhythm Pack ","Adventure MT ","Camo Adventure MT "];
            break;
        case "Elevate":
            variants=["Select", "SV MT","V CVT","ZX MT","V MT"];
            break;
        case "Amaze":
            variants=["Select", "Amaze E 1.2 Petrol MT","Amaze S 1.2 Petrol MT","Amaze S 1.2 Petrol CVT","Amaze VX 1.2 Petrol MT"];
            break;
        case "Aura":
            variants = ["Select", "Aura E 1.2 Petrol","Aura S 1.2 Petrol","Aura SX 1.2 Petrol","Aura S 1.2 CNG"];
            break;

case "Creta":
            variants = ["Select", "SX DT", "SX Tech DT", "SX (O) DT", "SX Tech iVT DT", "SX"];
            break;
case "New Verna":
            variants = ["Select", "EX", "S", "SX", "SX IVT", "SX Opt", "SX Turbo", "SX Turbo DT", "SX Opt Turbo", "SX Opt Turbo DT", "SX Turbo DCT", "SX Turbo DCT DT", "SX Opt IVT", "SX Opt Turbo DCT", "SX"];
            break;
case "Nano":
            variants = ["Select", "CX", "Twist XE", "LX", "CNG emax CE"];
            break;
case "Elevate":
            variants = ["Select", "SV", "V", "V CVT", "VX", "VX CVT", "ZX", "ZX CVT"];
            break;
case "Amaze":
            variants = ["Select", "CVT", "VX CVT", "VX", "S CVT", "S", "E"];
            break;
case "City":
            variants = ["Select","Elegant Edition", "Elegant Edition CVT", "SV", "V", "VX", "V CVT", "ZX", "VX CVT","ZX CVT"];
            break;



        default:
            break;
    }

    for (var i = 0; i < variants.length; i++) {
        var option = document.createElement("option");
        option.text = variants[i];
        variantSelect.add(option);
    }
   
    variantSelect.style.display = "block";
    variantLabel.style.display = "block";
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

// Function to handle selection of transmission type
function selectTransmission(event, transmissionType) {
    // Get all transmission buttons
    var transmissionButtons = document.querySelectorAll('#main #transmission-button button');
    
    // Deselect all transmission buttons
    transmissionButtons.forEach(function(button) {
        button.classList.remove('selected');
    });

    // Select the clicked transmission button
    event.target.classList.add('selected');

    // Set the selected transmission type value to some hidden input field or handle it as needed
    document.getElementById('transmission').value = transmissionType;
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