function updateSleepQualityValue(value) { // define function to update text content of HTML element within ID sleepQualityValue 
    document.getElementById('sleepQualityValue').textContent = value;
}
    // listen for clicks on button with ID addInterruption --> add new interruptions dynamically 
    document.getElementById('addInterruption').addEventListener('click', function() {
        var interruptionsFieldset = document.getElementById('interruptionsFieldset'); // fetch interruptionsFieldset 
        var interruptionCount = interruptionsFieldset.getElementsByClassName('interruption-entry').length; // count how many elements with class interruption-entry

        // Create new interruption entry
        var newInterruptionEntry = document.createElement('div'); // create new div for interruption element 
        newInterruptionEntry.classList.add('interruption-entry'); // add to interruption entry class  
        
        var timeLabel = document.createElement('label'); // create new label element 
        timeLabel.setAttribute('for', 'interruptionTime' + (interruptionCount + 1)); // give it a unique id 
        timeLabel.textContent = 'Time:'; // set text inside the label to time 

        var timeInput = document.createElement('input'); // create a new div for input 
        timeInput.setAttribute('type', 'time'); // set type element to time to allow users to input time 
        timeInput.setAttribute('id', 'interruptionTime' + (interruptionCount + 1)); // set id 
        timeInput.setAttribute('name', 'interruptionTime' + (interruptionCount + 1)); // set name 

        var reasonLabel = document.createElement('label'); // create new label element for interruption reason 
        reasonLabel.setAttribute('for', 'interruptionReason' + (interruptionCount + 1)); // sets for attribute matching the ID of corresponding input element 
        reasonLabel.textContent = 'Reason:'; //set textContent 

        var reasonInput = document.createElement('input'); // create input element
        reasonInput.setAttribute('type', 'text'); // set type as text 
        reasonInput.setAttribute('id', 'interruptionReason' + (interruptionCount + 1)); // set id and name dyanmically 
        reasonInput.setAttribute('name', 'interruptionReason' + (interruptionCount + 1));

        // append new elements to newInterruptionentry div 
        newInterruptionEntry.appendChild(timeLabel);
        newInterruptionEntry.appendChild(timeInput);
        newInterruptionEntry.appendChild(reasonLabel);
        newInterruptionEntry.appendChild(reasonInput);

        // Insert the new entry before the button
        interruptionsFieldset.insertBefore(newInterruptionEntry, this);
    });
    function addActivity() { // define function to add activities 
        var activitiesList = document.getElementById('activitiesList'); // fetch the values entered in the newActivity input field 
        var newActivity = document.getElementById('newActivity').value.trim(); // strip whitespace 
        if(newActivity) { // if not empty 
            // create checkbox for the new activity
            var checkbox = document.createElement('input');
            checkbox.type = 'checkbox'; // type is checkbox 
            checkbox.name = 'activitiesBeforeSleep'; // set name 
            checkbox.value = newActivity; // insert Newactivity 
            
            // Create label for the new checkbox
            var label = document.createElement('label');
            label.textContent = newActivity; 
            label.insertBefore(checkbox, label.firstChild); // insert befrore the label's text 
            
            // Add the new checkbox and label to the list of activities
            activitiesList.appendChild(label);
            
            // Clear the input field for new activity
            document.getElementById('newActivity').value = '';
        }
    }

    document.getElementById('addActivityBtn').addEventListener('click', addActivity); // submit event listener to listen for click on button for sleepLogForm 



document.getElementById("sleepLogForm").addEventListener("submit", function(e) {
    e.preventDefault(); // prevent form's deault submit action to send POST request and reload page 

    const formData = { // create form data object 
        date: document.getElementById('date').value, // grab date value from date input field 
        sleepStart: document.getElementById('sleepStart').value, // grab start time 
        sleepEnd: document.getElementById('sleepEnd').value, // grab end time 
        quality: document.getElementById('sleepQuality').value, // grab sleep quality 
        caffeineIntake: document.querySelector('input[name="caffeineIntake"]:checked').value === '1', // if true, grab 1
        alcoholIntake: document.querySelector('input[name="alcoholIntake"]:checked').value === '1',  // if true, grab 1
        environmentalFactors: { // grab EF elements 
                lightLevel: document.getElementById('lightLevel').value,
                noiseLevel: document.getElementById('noiseLevel').value,
                temperature: document.getElementById('temperature').value
            },
        interruptions: collectInterruptions(), // call this function to grab interruptions formatted 
        activitiesBeforeSleep: collectActivities() // call this function to grab activities formatted 
    };

    fetch("http://localhost:8000/api/addSleepData", {
        method: "POST", // make a post request with FeTCh api to request server to add sleep data 
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData) // turn form data object into a JSON but as a string 
    })
    .then(response => response.json()) // process JSON response from the server 
    .then(data => {
        console.log(data); // return the data to double check in console 
        alert("Sleep log added successfully!"); // return successful status 
    })
    .catch((error) => { // catch and throw errors
        console.error('Error:', error);
        alert("Failed to add sleep log.");
    });
});

function collectInterruptions() { // define function to collect interruptions 
    const interruptions = []; // initialize empty array 
    document.querySelectorAll('.interruption-entry').forEach(entry => { // iterate over each interruption entry 
        const time = entry.querySelector('[name^="interruptionTime"]').value; // collect time 
        const type = entry.querySelector('[name^="interruptionReason"]').value; // collect reason 
        if (time && type) { // if both fields are filled
            interruptions.push({time, type}); // append to interruptions array 
        }
    });
    return interruptions; // return 
}

function collectActivities() { // define function to collect activities before sleep 
    const activities = []; // initialize empty array 
    document.querySelectorAll('input[name="activitiesBeforeSleep"]:checked').forEach(checkbox => { // iteratore over each activity (denoted by checkbox)
        activities.push(checkbox.value); // append to activities array 
    });
    return activities; // return array 
}


