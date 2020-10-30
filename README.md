# aws-image-upload

This is a __Spring Boot__ project that allows you to upload images for a user and then displays the images on the same site. It's an almost-full stack project,
since I am using an in-memory database instead of a real database, and it utilizes Spring Boot and AWS S3 Buckets.

## The frontend is built with React, but I have not included it in the reposistory because I can't figure out the submodule problem. To build a frontend, there are only 2 calls you need to know:

`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload` is the url to upload an image (assuming you're hosting it on port 8080).


`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download` is the url to download/display an image (assuming you're hosting it on port 8080).

I recommend using React-Dropzone for the frontend, and Axios to make the API calls cleaner and much easier to understand.

Below is the Dropzone function I used for the Frontend:

```
function Dropzone({userProfileId}) { //react-dropzone function
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];

    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
    formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    }).then(() => {
      console.log("file uploaded successfully");
    }).catch(err => {
      console.log(err);
    });

  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here</p> :
          <p>Drag and drop images, or click to select images</p>
      }
    </div>
  )
}
```




