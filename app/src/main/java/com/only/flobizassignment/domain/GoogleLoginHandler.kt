package com.only.flobizassignment.domain

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import androidx.credentials.CredentialManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val WED_ID = "168578407811-ainvthvsebojabckikd8qb9cek9j04nt.apps.googleusercontent.com"

class GoogleLoginHandler @Inject constructor(
    private val auth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val WEB_CLIENT_ID = WED_ID
    }

    suspend fun signInWithGoogle(): Result<FirebaseUser> = withContext(Dispatchers.IO) {
        try {
            val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(WEB_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            val credential = result.credential
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val googleIdToken = googleIdTokenCredential.idToken

            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = auth.signInWithCredential(firebaseCredential).await()
            Result.success(authResult.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
