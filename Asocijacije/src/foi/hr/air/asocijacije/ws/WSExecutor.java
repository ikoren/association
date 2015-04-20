package foi.hr.air.asocijacije.ws;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

//ako nasljeduje AsyncTask znaci da se odvija u pozadinskoj dretvi
//progres dialog se vrti dok ja cekam da se ovo izvrsi u pozadini
public class WSExecutor extends AsyncTask<Object, String, String> {
	String result;
	ProgressDialog mDialog;
	public ResultHandler resultHandler;
	Context context;
	String url = null;

	public WSExecutor(Context context) {
		this.context = context;
		mDialog = new ProgressDialog(context);

	}

	// prikaz progress dialog
	@Override
	protected void onPreExecute() {
		// prvo se bude izvrsilo u super klasi, a onda ovo tu
		super.onPreExecute();
		if (!mDialog.isShowing()) {
			mDialog.setMessage("Loading...");
			mDialog.setCancelable(false);
			mDialog.setProgressNumberFormat("%1d/%2d kB");
			mDialog.show();
		}
	}

	//ako je ... moze primiti bilo kolko parametara
	@Override
	protected String doInBackground(Object... params) {

		url = (String) params[0];
		resultHandler = (ResultHandler) params[1];
		ServiceHandler serviceHandler = new ServiceHandler();
		result = serviceHandler.makeServiceCall(url, ServiceHandler.GET);
		//result je string sa svim podacima iz tablica sa servera
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (mDialog.isShowing()) {

			//mDialog.cancel();
		}
		if (resultHandler != null) {
			resultHandler.result(result);
		}
		resultHandler = null;
	}
}
