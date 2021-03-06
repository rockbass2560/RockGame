package com.rockbass2560.megacode.adapters;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rockbass2560.megacode.Claves;
import com.rockbass2560.megacode.R;
import com.rockbass2560.megacode.models.database.NivelConTerminado;
import com.rockbass2560.megacode.models.database.NivelTerminado;
import com.rockbass2560.megacode.views.fragments.InfoNivelFragment;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRecyclerSkillTree extends RecyclerView.Adapter<AdapterRecyclerSkillTree.SkillTreeViewHolder>{

    private LinkedList<List<NivelConTerminado>> nodes = new LinkedList<>();
    private MediaPlayer mediaPlayerSound;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Bundle bundle;

    public AdapterRecyclerSkillTree(FragmentManager fragmentManager, Fragment fragment){
        this.fragmentManager = fragmentManager;
        this.fragment = fragment;
    }

    public void setMediaPlayerSound(MediaPlayer mediaPlayerSound) {
        this.mediaPlayerSound = mediaPlayerSound;
    }

    @NonNull
    @Override
    public SkillTreeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View linearLayout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skillrow_layout,viewGroup,false);

        return new SkillTreeViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillTreeViewHolder skillTreeViewHolder, int index) {
        LinearLayout linearLayout = (LinearLayout)skillTreeViewHolder.itemView;
        linearLayout.removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(linearLayout.getContext());

        List<NivelConTerminado> horizontalNode = nodes.get(index);
        int cantidadNodos = horizontalNode.size();
        int nodosTerminados = 0;
        final boolean bloquear = bloquearNodos;

        for (NivelConTerminado nivelConTerminado : horizontalNode){
            View cardView = layoutInflater.inflate(R.layout.skillnode_layout,linearLayout, false);
            ImageView imageView = cardView.findViewById(R.id.node_imageview);

            //Nivel nivel = nivelConTerminado.nivel;
            List<NivelTerminado> nivelesTerminados = nivelConTerminado.nivelesTerminados;

            int puntaje=0;

            if (bloquearNodos) {
                //Cambiar el fondo de la vista a gris
                imageView.setBackgroundColor(cardView.getResources().getColor(R.color.md_grey_300));
            }else{

                if (!nivelesTerminados.isEmpty() && nivelesTerminados.get(0).terminado) {
                    nodosTerminados++;
                    puntaje = nivelesTerminados.get(0).puntaje;
                }

                ProgressBar progressBar = cardView.findViewById(R.id.skillnode_layout_progress);
                if (puntaje==100){
                    //Cambiar a dorado
                    Resources resources = imageView.getResources();
                    imageView.setImageTintList(ColorStateList.valueOf(resources.getColor(R.color.md_yellow_900)));
                    imageView.setBackgroundColor(resources.getColor(R.color.md_yellow_500));

                    //progressBar.setProgressBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.md_yellow_300)));
                }
                progressBar.setProgress(puntaje);
            }

            final int puntajeFinal = puntaje;

            imageView.setImageResource(nivelConTerminado.getImageResource(bloquearNodos));

            imageView.setOnClickListener(view -> {
                DialogFragment dialogFragment = new InfoNivelFragment(fragment);

                bundle.putParcelable("nivel", nivelConTerminado);
                bundle.putBoolean("bloqueado", bloquear);
                bundle.putInt("puntaje", puntajeFinal);

                int[] locations= new int[2];
                view.getLocationOnScreen(locations);
                bundle.putInt("sourceX", locations[0]);
                bundle.putInt("sourceY", locations[1]);

                bundle.putInt("heightView", view.getHeight());

                mediaPlayerSound.seekTo(0);
                mediaPlayerSound.start();

                dialogFragment.setArguments(bundle);
                dialogFragment.show(fragmentManager, Claves.INFO_NIVEL_FRAGMENT_TAG);
            });

            linearLayout.addView(cardView);
        }

        bloquearNodos = cantidadNodos != nodosTerminados;
    }

    private boolean bloquearNodos = false;

    public void setData(LinkedList<List<NivelConTerminado>> nodes, Bundle data){
        this.nodes = nodes;
        bloquearNodos = false;
        this.bundle = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    public class SkillTreeViewHolder extends RecyclerView.ViewHolder{

        public SkillTreeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
