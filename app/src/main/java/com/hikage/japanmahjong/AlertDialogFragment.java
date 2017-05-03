package com.hikage.japanmahjong;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment {

    public static String KEY_CONTINUE = "continue";//繼續牌局
    public static String KEY_CHANGESETTING = "changeSetting";//修改設定
    public static String KEY_CANCELRICHI = "cancelRichi";//取消立直
    public static String KEY_DRAWGAME = "drawGame";//流局
    public static String KEY_CHONBO = "chonbo";//犯規
    public static String KEY_PAUSEGAME = "pauseGame";//中斷牌局
    public static String KEY_CLICKMENU = "clickMenu";//clickMenu

    public static AlertDialogFragment newInstance(String id, String clickPlayer) {
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();

        args.putString("id", id);

        if (clickPlayer == null) {
            String title, message;
            if (id.equals(KEY_CONTINUE)) {
                title = "尚有牌局未結束，請問是否繼續？";
                message = "";
            } else if (id.equals(KEY_CHANGESETTING)) {
                title = "請問是否要修改設定？";
                message = "修改設定將會清除未完成之牌局";
            } else if (id.equals(KEY_CANCELRICHI)) {
                title = "確定要取消立直嗎？";
                message = "";
            } else if (id.equals(KEY_DRAWGAME)) {
                title = "請問是否要流局？";
                message = "";
            } else if (id.equals(KEY_CHONBO)) {
                title = "犯規";
                message = Game.setChonboMessage();
            } else if (id.equals(KEY_PAUSEGAME)) {
                title = "請問是否要中斷牌局？";
                message = "中斷後將會自動記錄目前牌局";
            } else {
                title = "";
                message = "";
            }
            args.putString("title", title);
            args.putString("message", message);
        } else {
            String title;
            switch (clickPlayer) {
                case "0":
                    title = ConstantUtil.getPlayer(0);
                    break;
                case "1":
                    title = ConstantUtil.getPlayer(1);
                    break;
                case "2":
                    title = ConstantUtil.getPlayer(2);
                    break;
                default:
                    title = ConstantUtil.getPlayer(3);
                    break;
            }
            String[] list = {"榮", "自摸", "立直"};
            args.putString("title", title);
            args.putStringArray("list", list);
        }

        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String id = getArguments().getString("id");
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");
        String list[] = getArguments().getStringArray("list");

        if (id.equals(KEY_CONTINUE) || id.equals(KEY_CHANGESETTING))
            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity) getActivity()).doPositiveClick(getArguments().getString("id"));
                                }
                            }
                    )
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity) getActivity()).doNegativeClick(getArguments().getString("id"));
                                }
                            }
                    )
                    .create();
        else if (id.equals(KEY_CANCELRICHI) || id.equals(KEY_DRAWGAME) ||
                id.equals(KEY_CHONBO) || id.equals(KEY_PAUSEGAME))
            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((Game) getActivity()).doPositiveClick(getArguments().getString("id"));
                                }
                            }
                    )
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((Game) getActivity()).doNegativeClick();
                                }
                            }
                    )
                    .create();
        else if (id.equals(KEY_CLICKMENU))
            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setItems(list,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    ((Game) getActivity()).doItemClick(which);
                                }
                            })
                    .create();
        else
            return null;
    }

    public void onCancel(DialogInterface dialog){
        if (getActivity().getClass() == Game.class)
            ((Game) getActivity()).checkConfiguration();
    }
}
