// function to populate models based on selected brand
function showModels() {
	var brandSelect = document.getElementById("brands");

}

  // Function to populate cities based on selected state
function populateCities() {
    var stateSelect = document.getElementById("country-state");
    var citySelect = document.getElementById("city");

    // Clear previous options
    citySelect.innerHTML = "";

    // Get the selected state
    var selectedState = stateSelect.value;

    // Define cities based on the selected state
    var cities = [];
    switch (selectedState) {
        case "AN":
            cities = ["Select", "Port Blair", "Ahirkhedi", "Manglish"];
            break;
        case "AP":
            cities = ["Select", "Visakhapatnam", "Vijayawada", "Guntur"];
            break;
        case "MP":
          cities = ["Select", "Indore", "Bhopal", "Gwalior", "Jabalpur", "Ujjain", "Sagar", "Ratlam", "Rewa", "Satna", "Dewas"];
          break;   
        case "MH":
            cities = ["Select", "Mumbai", "Pune", "Nagpur", "Nashik", "Aurangabad", "Solapur", "Amravati", "Jalgaon", "Kolhapur", "Sangli"];
            break;     
        case "GJ":
            cities = ["Select", "Ahmedabad", "Surat", "Vadodara", "Rajkot", "Gandhinagar", "Jamnagar", "Bhavnagar"];
                break;  
        case "RJ":
            cities = ["Select", "Jaipur", "Jodhpur", "Udaipur", "Ajmer", "Kota"];
           break;  
         case "GA":
         cities = ["Select","Panaji", "Margao", "Vasco da Gama", "Mapusa", "Ponda" ];
            break;  
        case "UP":
            cities = ["Select", 'Lucknow', 'Kanpur', 'Agra', 'Varanasi', 'Meerut'];
            break;  
        case "TN":
            cities = ["Select", 'Chennai', 'Coimbatore', 'Madurai', 'Tiruchirappalli', 'Salem'];
            break;  
        case "KA":
            cities = ["Select", 'Bangalore', 'Mysore', 'Hubli', 'Belgaum', 'Mangalore'];
            break;  
        case "WB":
            cities = ["Select","Kolkata", "Asansol", "Siliguri", "Durgapur", "Howrah", "Darjeeling", "Malda", "Kharagpur", "Berhampore", "Bardhaman" ];
            break;  
         case "DL":
            cities = ["Select", 'Delhi', 'New Delhi', 'Noida', 'Gurgaon', 'Faridabad'];
            break;                
        case "JK":
            cities =["Select",'Srinagar', 'Jammu', 'Anantnag', 'Baramulla', 'Kathua'];
            break;
        case "HR":
             cities =["Select",'Faridabad', 'Gurgaon', 'Panipat', 'Ambala', 'Yamunanagar', 'Rohtak', 'Hisar', 'Karnal', 'Sonipat', 'Panchkula'];
             break;
        case "PB":
             cities =["Select","Ludhiana", "Amritsar", "Jalandhar", "Patiala", "Bathinda"];
             break;
        case "BR":
             cities =["Select","Patna", "Gaya", "Bhagalpur", "Muzaffarpur", "Darbhanga", "Arrah", "Begusarai"];
             break;
        
        
        default:
            break;
    }

    // Add options to the city select tag
    for (var i = 0; i < cities.length; i++) {
        var option = document.createElement("option");
        option.text = cities[i];
        citySelect.add(option);
    }

    // Show the city select tag
    citySelect.style.display = "block";
    document.getElementById("city-label").style.display = "block";
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
    document.getElementById('selectedOwners').value = ownerType;
}

 // Validation for Year
 document.getElementById("year").addEventListener("input", function() {
    var year = this.value;
    if (year < 1980) {
        document.getElementById("year-error").textContent = "Year must be at least 1980";
        this.value = 1980;
    } else {
        document.getElementById("year-error").textContent = "";
    }
});

 


// Validation for Title
document.getElementById("title").addEventListener("input", function() {
    var title = this.value;
    if (title.length > 70) {
        document.getElementById("title-error").textContent = "Maximum 70 characters allowed";
        this.value = title.slice(0, 70);
    } else {
        document.getElementById("title-error").textContent = "";
    }
});

// Validation for Description
document.getElementById("description").addEventListener("input", function() {
    var description = this.value;
    if (description.length > 5000) {
        document.getElementById("description-error").textContent = "Maximum 5000 characters allowed";
        this.value = description.slice(0, 5000);
    } else {
        document.getElementById("description-error").textContent = "";
    }
});

