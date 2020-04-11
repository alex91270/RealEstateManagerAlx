
package com.example.realestatemanageralx.viewmodels;

        import android.app.Application;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;

        import com.example.realestatemanageralx.database.AgentDao;
        import com.example.realestatemanageralx.database.AppDatabase;
        import com.example.realestatemanageralx.database.PropertyDAO;
        import com.example.realestatemanageralx.model.Agent;
        import com.example.realestatemanageralx.model.Property;

        import java.util.List;

/**
 * ViewModel for agents, using the DAO and returning LiveData, observed by the view
 */

public class AgentViewModel  extends AndroidViewModel {
    private AgentDao agentDao;
    private LiveData<Agent> agentLiveData;

    public AgentViewModel (@NonNull Application application) {
        super(application);
        agentDao = AppDatabase.getDatabase(application).agentDAO();
    }

    public LiveData<Agent> getAgentById(long id) {
        agentLiveData = agentDao.getAgentById(id);
        return agentLiveData;
    }

    public LiveData<Agent> getAgentByUsername(String name) {
        agentLiveData = agentDao.getAgentByUsername(name);
        return agentLiveData;
    }
}



