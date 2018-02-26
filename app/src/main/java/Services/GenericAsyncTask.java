package Services;

import android.os.AsyncTask;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import Communication.ServerProxy;
import Presenters.IPresenter;
import common.Results;

/**
 * Created by ephraimkunz on 2/22/18.
 */

public class GenericAsyncTask extends AsyncTask<Object, Void, Results> {
    private String methodName;
    private IPresenter presenter;
    private String[] paramTypeNames;

    public GenericAsyncTask(IPresenter presenter, String methodName, String[] paramTypes) {
        this.presenter = presenter;
        this.methodName = methodName;
        this.paramTypeNames = paramTypes;
    }

    @Override
    protected Results doInBackground(Object... objects) {
        Results result = new Results(false, "", "ERROR: could not execute async task!");

        try {
            Class<?>[] _paramTypes = new Class<?>[paramTypeNames.length];
            for (int i = 0; i < paramTypeNames.length; i++) {
                _paramTypes[i] = Class.forName(paramTypeNames[i]);
            }

            Object singleton = ServerProxy.getInstance();
            Method[] m = ServerProxy.class.getMethods();
            Method method = ServerProxy.class.getMethod(methodName, _paramTypes);
            Object o = method.invoke(singleton, objects);
            result = (Results) o;
        }
        catch (Exception e) {
            e.printStackTrace();

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stackTrace = sw.toString();
            result = new Results(false, "", "Error: " + e.toString() + "\n" + stackTrace);
        }
        finally {
            return result;
        }
    }

    @Override
    protected void onPostExecute(Results results) {
        presenter.onPostExecute(results);
    }
}
