
	    /* set region */
	    AWS.config.region = 'us-west-2'; 
		/* cognito id */
	    AWS.config.credentials = new AWS.CognitoIdentityCredentials({
	        IdentityPoolId: ''
	    });
		/* set credentials */
	    AWS.config.credentials.get(function(err) {
	        if (err) alert(err);
	        console.log(AWS.config.credentials);
	    });
	    /* add bucket name */
	    var bucketName = ''';
	    /* s3 instance */
	    var bucket = new AWS.S3({
	        params: {
	            Bucket: bucketName
	        }
	    });
	    /* pull in html elements */
	    var fileChooser = document.getElementById('file-chooser');
	    var button = document.getElementById('upload-button');
	    var results = document.getElementById('results');
	    /* button listener */
	    button.addEventListener('click', function() {
	    	/* chooses file in number 1 spot */
	        var file = fileChooser.files[0];
	        /* if there is a file */
	        if (file) {
	
	            results.innerHTML = '';
	            var objKey = '' + file.name;
	            var params = {
	                Key: objKey,
	                ContentType: file.type,
	                Body: file,
	                ACL: 'public-read'
	            };
	
	            bucket.putObject(params, function(err, data) {
	                if (err) {
	                    results.innerHTML = 'ERROR: ' + err;
	                } else {
	                	alert("File has been uploaded!");
	                    listObjs();
	                }
	            });
	            /* no file */
	        } else {
	            results.innerHTML = 'Nothing to upload.';
	        }
	    }, false);
	    
	    /* list all the objects in a div tag with id results */
	    function listObjs() {
	        var prefix = '';
	        bucket.listObjects({
	            Prefix: prefix
	        }, function(err, data) {
	            if (err) {
	                results.innerHTML = 'ERROR: ' + err;
	            } else {
	                var objKeys = "";
	                data.Contents.forEach(function(obj) {
	                    objKeys += obj.Key + "<br/>";
	                });
	                results.innerHTML = objKeys;
	            }
	        });
	    }
	    