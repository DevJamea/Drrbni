package com.example.drrbni.ViewModels;

import static com.example.drrbni.Constant.ADDRESS;
import static com.example.drrbni.Constant.ADS_ID;
import static com.example.drrbni.Constant.COLLECTION_ADS;
import static com.example.drrbni.Constant.COLLECTION_CATEGORIES;
import static com.example.drrbni.Constant.COLLECTION_JOBS;
import static com.example.drrbni.Constant.COLLECTION_MAJORS;
import static com.example.drrbni.Constant.COLLECTION_NOTIFICATION;
import static com.example.drrbni.Constant.COLLECTION_PROFILE_COMPANIES;
import static com.example.drrbni.Constant.COLLECTION_UNIVERSITIES;
import static com.example.drrbni.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbni.Constant.COLLEGE;
import static com.example.drrbni.Constant.EMAIL;
import static com.example.drrbni.Constant.GOVERNORATE;
import static com.example.drrbni.Constant.IMG;
import static com.example.drrbni.Constant.JOB_DESCRIPTION;
import static com.example.drrbni.Constant.JOB_ID;
import static com.example.drrbni.Constant.JOB_LINK;
import static com.example.drrbni.Constant.JOB_NAME;
import static com.example.drrbni.Constant.MAJOR;
import static com.example.drrbni.Constant.MAJOR_NAME;
import static com.example.drrbni.Constant.NAME;
import static com.example.drrbni.Constant.NOTIFICATION_RECIPIENT;
import static com.example.drrbni.Constant.PREF_STATE_AUTH;
import static com.example.drrbni.Constant.PROFILE_COMPANIES_MAJOR;
import static com.example.drrbni.Constant.REQUESTS;
import static com.example.drrbni.Constant.STATE_AUTH;
import static com.example.drrbni.Constant.STUDENT_TYPE;
import static com.example.drrbni.Constant.TOKEN;
import static com.example.drrbni.Constant.TYPE_USER;
import static com.example.drrbni.Constant.UID;
import static com.example.drrbni.Constant.UNIVERSITY_NAME;
import static com.example.drrbni.Constant.USER_ID;
import static com.example.drrbni.Constant.WHATSAPP;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbni.MajorTopic;
import com.example.drrbni.Models.Ads;
import com.example.drrbni.Models.Category;
import com.example.drrbni.Models.Company;
import com.example.drrbni.Models.Job;
import com.example.drrbni.Models.Notification;
import com.example.drrbni.Models.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {

    private Application application;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseMessaging firebaseMessaging;
    private MutableLiveData<Student> profileInfo;
    private MutableLiveData<List<Job>> jobsData;
    private SharedPreferences stateAuth;

    public Repository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseMessaging = FirebaseMessaging.getInstance();
        profileInfo = new MutableLiveData<>();
        jobsData = new MutableLiveData<>();
    }


    public void signUp(String email, String password,
                       MyListener<FirebaseUser> isSuccessful, MyListener<String> isFailure) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isSuccessful.onValuePosted(firebaseAuth.getCurrentUser());
                        } else {
                            isFailure.onValuePosted(task.getException().toString());
                        }
                    }
                });
    }

    public void storeSignUpData(FirebaseUser firebaseUser, String name, MyListener<Boolean> isSuccessful) {

        getToken(new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

                HashMap<String, Object> data = new HashMap<>();
                data.put(UID, firebaseUser.getUid());
                data.put(NAME, name);
                data.put(EMAIL, firebaseUser.getEmail());
                data.put(TYPE_USER, STUDENT_TYPE);
                data.put(TOKEN, value);

                firebaseFirestore.collection(COLLECTION_USERS_PROFILES).document(firebaseUser.getUid())
                        .set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                    }
                });

            }
        });

    }

    public void storeAddressData(String governorate, String address, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(GOVERNORATE, governorate);
        data.put(ADDRESS, address);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).
                document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);

                    }
                });
    }

    public void storeEducationData(String college, String major, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(COLLEGE, college);
        data.put(MAJOR, major);
        subscribeToTopic(major);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);

                    }
                });
    }

    public void storeContactData(String whatsapp, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(WHATSAPP, whatsapp);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                    }
                });
    }

    public void storeImg(Uri image, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseStorage.getReference()
                .child(("studentProfilePictures/" + image.getLastPathSegment()))
                .putFile(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> data = new HashMap<>();
                                data.put(IMG, uri.toString());

                                firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .update(data)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                    isSuccessful.onValuePosted(true);
                                            }
                                        });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }


    public void signIn(String email, String password, MyListener<Boolean> isSuccessful, MyListener<String> isFailure) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);
                else
                    isFailure.onValuePosted(task.getException().toString());
            }
        });
    }

    public void checkSignInData(String email, MyListener<Student> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .whereEqualTo(EMAIL, email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Student student = document.toObject(Student.class);
                                isSuccessful.onValuePosted(student);
                            }
                        } else {
                            isFailure.onValuePosted(true);
                        }
                    }
                });
    }

    public void resetPassword(String email, MyListener<Boolean> isSuccessful, MyListener<String> isFailure) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    isSuccessful.onValuePosted(true);
                } else {
                    isFailure.onValuePosted(task.getException().getMessage());
                }

            }
        });
    }

    public void requestProfileInfo(String uid) {
        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .whereEqualTo(UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Student student = document.toObject(Student.class);
                                profileInfo.postValue(student);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<Student> getProfileInfo() {
        return profileInfo;
    }

    public void storeJobData(String uid, Uri image, String jobName, String major, String jobLink,
                             String jobDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        firebaseStorage.getReference()
                .child(("JobImages/" + System.currentTimeMillis() + image.getLastPathSegment()))
                .putFile(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DocumentReference docRef = firebaseFirestore.collection(COLLECTION_JOBS).document();
                                Job job = new Job(docRef.getId(), uid, jobName, major, jobLink, jobDescription
                                        , uri.toString());
                                docRef.set(job);
                                isSuccessful.onValuePosted(true);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void requestGetJobs(String uid) {
        firebaseFirestore.collection(COLLECTION_JOBS)
                .whereEqualTo(USER_ID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Job> jobList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Job job = document.toObject(Job.class);
                                jobList.add(job);
                                jobsData.postValue(jobList);
                            }
                        }
                    }
                });
    }

    public MutableLiveData<List<Job>> getJobsData() {
        return jobsData;
    }

    public void getCategories(MyListener<List<Category>> isSuccessful) {
        firebaseFirestore.collection(COLLECTION_CATEGORIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Category> categoryList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categoryList.add(category);
                            }
                            isSuccessful.onValuePosted(categoryList);
                        }
                    }
                });
    }


    public void editProfileDataWithoutImage(String name,String college, String major,
                                            MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(COLLEGE, college);
        data.put(MAJOR, major);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseUser.getUid())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                        else
                            isFailure.onValuePosted(true);
                    }
                });
    }


    public void editProfileDataWithImg(Uri image, String studentName, String college, String major
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        firebaseStorage.getReference()
                .child("studentProfilePictures/" + System.currentTimeMillis() + image.getLastPathSegment())
                .putFile(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap<String, Object> data = new HashMap<>();
                                data.put(NAME, studentName);
                                data.put(IMG, uri.toString());
                                data.put(COLLEGE, college);
                                data.put(MAJOR, major);

                                firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                                        .document(firebaseUser.getUid())
                                        .update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            isSuccessful.onValuePosted(true);
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });
    }

    public void editProfileContactInformation(String whatsapp, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(WHATSAPP, whatsapp);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(firebaseAuth.getCurrentUser().getUid())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);
                    }
                });
    }

    public void editAddressData(String governorate, String address, MyListener<Boolean> isSuccessful) {
        HashMap<String, Object> data = new HashMap<>();
        data.put(GOVERNORATE, governorate);
        data.put(ADDRESS, address);

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES).
                document(firebaseAuth.getCurrentUser().getUid()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            isSuccessful.onValuePosted(true);

                    }
                });
    }

    public void changePassword(String currentPassword, String newPassword
            , MyListener<Boolean> isSuccessful, MyListener<String> isFailure) {

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), currentPassword);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    firebaseUser.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    isSuccessful.onValuePosted(true);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            isFailure.onValuePosted(e.toString());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }

    public void getColleges(MyListener<List<String>> isSuccessful) {
        firebaseFirestore.collection(COLLECTION_UNIVERSITIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> collegeList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                collegeList.add(document.getString(UNIVERSITY_NAME));
                            }
                            isSuccessful.onValuePosted(collegeList);
                        }
                    }
                });
    }

    public void getMajors(MyListener<List<String>> isSuccessful) {
        firebaseFirestore.collection(COLLECTION_MAJORS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> majorList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                majorList.add(document.getString(MAJOR_NAME));
                            }
                            isSuccessful.onValuePosted(majorList);
                        }
                    }
                });
    }


    public void getCompaniesByMajor(String major, MyListener<List<Company>> isSuccessful, MyListener<String> isFailure) {
        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .whereEqualTo(PROFILE_COMPANIES_MAJOR, major)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Company> companies = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company company = document.toObject(Company.class);
                                companies.add(company);
                            }
                            isSuccessful.onValuePosted(companies);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }

    public void getInfoCompanyByUID(String UserId, MyListener<Company> isSuccessful, MyListener<String> isFailure) {
        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .document(UserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Company company = document.toObject(Company.class);
                            isSuccessful.onValuePosted(company);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }

    public void getAdsByUid(String Uid, MyListener<List<Ads>> isSuccessful, MyListener<String> isFailure) {
        firebaseFirestore.collection(COLLECTION_ADS)
                .whereEqualTo(USER_ID, Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Ads> adsList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ads ads = document.toObject(Ads.class);
                                adsList.add(ads);
                            }
                            isSuccessful.onValuePosted(adsList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }


    public void getCompany(String uid, MyListener<Company> company) {
        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .whereEqualTo(UID, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company com = document.toObject(Company.class);
                                company.onValuePosted(com);
                            }
                        }
                    }
                });
    }

    public void deleteJob(String jobId, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        firebaseFirestore.collection(COLLECTION_JOBS)
                .document(jobId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            isSuccessful.onValuePosted(true);
                        } else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void getJobById(String jobId, MyListener<Job> isSuccessful
            , MyListener<Boolean> isFailure) {
        firebaseFirestore.collection(COLLECTION_JOBS)
                .document(jobId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Job Job = document.toObject(Job.class);
                            isSuccessful.onValuePosted(Job);
                        } else
                            isFailure.onValuePosted(true);
                    }
                });
    }


    public void editJobData(String jobId, Uri image, String jobName, String major, String jobLink,
                            String jobDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        firebaseStorage.getReference()
                .child(("JobImages/" + System.currentTimeMillis() + image.getLastPathSegment()))
                .putFile(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> job = new HashMap<>();
                                job.put(JOB_ID, jobId);
                                job.put(IMG, uri.toString());
                                job.put(MAJOR, major);
                                job.put(JOB_NAME, jobName);
                                job.put(JOB_LINK, jobLink);
                                job.put(JOB_DESCRIPTION, jobDescription);

                                firebaseFirestore.collection(COLLECTION_JOBS)
                                        .document(jobId)
                                        .update(job).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            isSuccessful.onValuePosted(true);
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(true);
            }
        });

    }

    public void editJobDataWithoutImg(String jobId, String jobName, String major, String jobLink,
                                      String jobDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        HashMap<String, Object> job = new HashMap<>();
        job.put(JOB_ID, jobId);
        job.put(MAJOR, major);
        job.put(JOB_NAME, jobName);
        job.put(JOB_LINK, jobLink);
        job.put(JOB_DESCRIPTION, jobDescription);

        firebaseFirestore.collection(COLLECTION_JOBS)
                .document(jobId)
                .update(job).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    isSuccessful.onValuePosted(true);
                else
                    isFailure.onValuePosted(true);
            }
        });

    }

    public void getCompanyImgAndName(String companyId, MyListener<Company> company) {
        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .whereEqualTo(UID, companyId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company com = document.toObject(Company.class);
                                company.onValuePosted(com);
                            }
                        }
                    }
                });
    }

    public void getToken(MyListener<String> isSuccessful) {

        firebaseMessaging.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    isSuccessful.onValuePosted(task.getResult());
                }
            }
        });
    }

    public void updateToken(String newToken) {
        if (firebaseUser != null) {
            firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                    .document(firebaseUser.getUid())
                    .update(TOKEN, newToken);
        }

    }

    public void subscribeToTopic(String major) {

        MajorTopic majorTopic = MajorTopic.getInstance();
        firebaseMessaging.subscribeToTopic(majorTopic.getMajorTopic(major));
    }

    public void updateTopic(String oldTopic, String newTopic) {

        firebaseMessaging.unsubscribeFromTopic(oldTopic);
        firebaseMessaging.subscribeToTopic(newTopic);

    }

    public void getTokenByCompanyId(String companyId, MyListener<String> isSuccessful) {

        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .document(companyId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String token = document.getString(TOKEN);
                    isSuccessful.onValuePosted(token);
                }
            }
        });
    }

    public void getNameByUid(String Uid, MyListener<String> isSuccessful) {

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String name = document.getString(NAME);
                    isSuccessful.onValuePosted(name);
                }
            }
        });

    }

    public void addToRequestArray(String adsId) {

        firebaseFirestore.collection(COLLECTION_ADS)
                .document(adsId)
                .update(REQUESTS, FieldValue.arrayUnion(firebaseUser.getUid()));


    }

    public void storeNotification(String senderUid, String title, String body, String adsId) {

        DocumentReference docRef = firebaseFirestore.collection(COLLECTION_NOTIFICATION).document();
        Notification notification = new Notification(docRef.getId(), senderUid, firebaseUser.getUid(),
                title, body, adsId);
        docRef.set(notification);

    }

    public void getNotificationByUid(String uid, MyListener<List<Notification>> isSuccessful
            , MyListener<Boolean> isFailure) {

        firebaseFirestore.collection(COLLECTION_NOTIFICATION)
                .whereEqualTo(NOTIFICATION_RECIPIENT, uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            List<Notification> notifications = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notification notification = document.toObject(Notification.class);
                                notifications.add(notification);
                            }
                            isSuccessful.onValuePosted(notifications);
                        } else
                            isFailure.onValuePosted(true);
                    }
                });
    }

    public void getSenderImgAndName(String senderUid, MyListener<Company> company) {
        firebaseFirestore.collection(COLLECTION_PROFILE_COMPANIES)
                .whereEqualTo(UID, senderUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Company com = document.toObject(Company.class);
                                company.onValuePosted(com);
                            }
                        }
                    }
                });
    }

    public void getAdsById(String adsId, MyListener<Ads> isSuccessful) {
        firebaseFirestore.collection(COLLECTION_ADS)
                .whereEqualTo(ADS_ID, adsId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Ads ads = document.toObject(Ads.class);
                                isSuccessful.onValuePosted(ads);
                            }
                        }
                    }
                });
    }

    public void getEmail(String uid, MyListener<String> isSuccessful
            , MyListener<Boolean> isFailure) {

        firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Company company = document.toObject(Company.class);
                            String email = company.getEmail();
                            isSuccessful.onValuePosted(email);
                        } else {
                            isFailure.onValuePosted(false);
                        }
                    }
                });
    }

    public void changeEmail(String currentPassword, String newEmail, String uid, MyListener<Boolean> isSuccessful
            , MyListener<String> isFailure) {

        AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), currentPassword);
        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                firebaseFirestore.collection(COLLECTION_USERS_PROFILES)
                                        .document(uid)
                                        .update(EMAIL, firebaseUser.getEmail())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    isSuccessful.onValuePosted(true);
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isFailure.onValuePosted(e.getMessage());
            }
        });
    }

    public void SignOut(Activity activity) {
        stateAuth = activity.getSharedPreferences(PREF_STATE_AUTH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stateAuth.edit();
        editor.putBoolean(STATE_AUTH, false);
        firebaseAuth.signOut();
    }

}
