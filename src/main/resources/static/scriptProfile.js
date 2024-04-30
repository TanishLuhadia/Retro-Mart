
const profilePic = document.getElementById("myImage");
			const inputFile = document.getElementById("input-image");
			const userFirstName = document.getElementById("userFirstName");
			const userEmail = document.getElementById("userEmail");
const userLastName = document.getElementById("userLastName");
			
			inputFile.onchange = function () {
				profilePic.src = URL.createObjectURL(inputFile.files[0]);
			};

			function openModal() {
				const currentFirstName = userFirstName.innerText;
				const currentLastName = userLastName.innerText;
	
				document.getElementById("editFirstName").value = currentFirstName;
				document.getElementById("editLastName").value = currentLastName;
				
				document.getElementById("editModal").style.display = "flex";
			}

			function closeModal() {
				document.getElementById("editModal").style.display = "none";
			}

			function saveChanges() {
				const newFirstName = document.getElementById("editFirstName").value;
				const newLastName = document.getElementById("editLastName").value;
				
				userFirstName.innerText = newFirstName;
				userLastName.innerText = newLastName;

$.ajax({
    url: "/getLoggedInUser",
    method: "GET",
    success: function(data) {
        $.ajax({
            url: "/user/getUser",
            method: "GET",
            data: {
                "email": data
            },
            success: function(user) {
                $.ajax({
                    url: "/user/update",
                    method: "POST",
                    data: {
                        "email": data,
"firstName":newFirstName,
"lastName":newLastName
                    },
                    success: function(update) {
                            
if(update=="Updated")
{
alert("Updated");
}
},










                    error: function(xhr, status, error) {
                        console.error("Failed to fetch car data:", error);
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error("Failed to fetch user data:", error);
            }
        });
    },
    error: function(xhr, status, error) {
        console.error("Failed to fetch logged-in user:", error);
    }
});






				closeModal();
			}

$(() => {
    $("#input-image").change((ev) => {
        // Create FormData object to handle file upload
        var formData = new FormData();
        formData.append('file1', ev.target.files[0]); // Assuming only one file is selected

        $.ajax({
            url: "/uploadProfile",
            method: "POST",
            data: formData,
            processData: false, // Don't process the data
            contentType: false, // Don't set content type
            success: function(data) {
                if (data == 'Uploaded') {
                    alert("Done");
                } else {
                    alert("Problem");
                }
            },
            error: function(xhr, status, error) {
                console.error("Failed to fetch image:", error);
                alert("Error uploading image. Please try again later.");
            }
        });
    });

    const dynamicContainer = document.getElementById("sec1");

    $.ajax({
        url: "/getLoggedInUser",
        method: "GET",
        success: function(data) {
            $.ajax({
                url: "/user/getUser",
                method: "GET",
                data: {
                    "email": data
                },
                success: function(user) {
                    $("#userFirstName").html(user.firstName);
                    $("#userLastName").html(user.lastName);
                    $("#userEmail").html(data);

                    $.ajax({
                        url: "/car/getByEmail",
                        method: "POST",
                        data: {
                            "email": data
                        },
                        success: function(cars) {
                            alert("sending request for get profile");
                            $.ajax({
                                url: "/getProfile",
                                method: "POST",
                                data: {
                                    "email": data
                                },
                                success: function(dataaa) {
                                    alert(dataaa + " in getProfile");
                                    if (dataaa.successful == true) {
                                        alert("/images/" + dataaa.result);
                                        profilePic.src = "/images/" + dataaa.result;
                                    } else {
                                        alert("/" + dataaa.result);
                                        profilePic.src = "/" + dataaa.result;
                                    }
                                },
                                error: function(xhr, status, error) {
                                    console.error("Failed to fetch image:", error);
                                    alert("Error uploading image. Please try again later.");
                                }
                            });

                            cars.forEach(function(car) {
                                $.ajax({
                                    url: "/image/getImageByCarId",
                                    method: "GET",
                                    data: {
                                        "carId": car.id
                                    },
                                    success: function(imageData) {
                                        const itemContainer = document.createElement("div");
                                        itemContainer.classList.add("imgContainer");

                                        const itemImage = document.createElement("img");
                                        itemImage.src = "/images/" + imageData.frontImage;

                                        const itemPrice = document.createElement("div");
                                        itemPrice.innerHTML = `<span>&#8377;</span><h1>${car.price}</h1>`;

                                        const itemDescription = document.createElement("p");
                                        itemDescription.textContent = `${car.year} - ${car.driven} km`;

                                        const itemLocation = document.createElement("div");
                                        itemLocation.classList.add("foot");
                                        itemLocation.innerHTML = `<h5>${car.city}</h5><h5>${car.date}</h5>`;

                                        itemContainer.appendChild(itemImage);
                                        itemContainer.appendChild(itemPrice);
                                        itemContainer.appendChild(itemDescription);
                                        itemContainer.appendChild(itemLocation);

                                        dynamicContainer.appendChild(itemContainer);
                                    },
                                    error: function(xhr, status, error) {
                                        console.error("Failed to fetch image:", error);
                                    }
                                });
                            });
                        },
                        error: function(xhr, status, error) {
                            console.error("Failed to fetch car data:", error);
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error("Failed to fetch user data:", error);
                }
            });

            $.ajax({
                url: "/bike/getByEmail",
                method: "POST",
                data: {
                    "email": data
                },
                success: function(bikes) {
                    bikes.forEach(function(bike) {
                        $.ajax({
                            url: "/image/getImageByBikeId",
                            method: "GET",
                            data: {
                                "bikeId": bike.id
                            },
                            success: function(imageData) {
                                const itemContainer = document.createElement("div");
                                itemContainer.classList.add("imgContainer");

                                const itemImage = document.createElement("img");
                                itemImage.src = "/images/" + imageData.frontImage;

                                const itemPrice = document.createElement("div");
                                itemPrice.innerHTML = `<span>&#8377;</span><h1>${bike.price}</h1>`;

                                const itemDescription = document.createElement("p");
                                itemDescription.textContent = `${bike.year} - ${bike.driven} km`;

                                const itemLocation = document.createElement("div");
                                itemLocation.classList.add("foot");
                                itemLocation.innerHTML = `<h5>${bike.city}</h5><h5>${bike.date}</h5>`;

                                itemContainer.appendChild(itemImage);
                                itemContainer.appendChild(itemPrice);
                                itemContainer.appendChild(itemDescription);
                                itemContainer.appendChild(itemLocation);

                                dynamicContainer.appendChild(itemContainer);
                            },
                            error: function(xhr, status, error) {
                                console.error("Failed to fetch image:", error);
                            }
                        });
                    });
                },
                error: function(xhr, status, error) {
                    console.error("Failed to fetch bike data:", error);
                }
            });
        },
        error: function(xhr, status, error) {
            console.error("Failed to fetch logged-in user:", error);
        }
    });
});
