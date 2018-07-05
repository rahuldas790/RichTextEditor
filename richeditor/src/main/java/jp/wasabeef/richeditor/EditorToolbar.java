package jp.wasabeef.richeditor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditorToolbar extends LinearLayout {

    private Context context;
    //root horizontal scroll view
    private HorizontalScrollView horizontalScrollView;

    public EditorToolbar(Context context) {
        this(context, null);
    }

    public EditorToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toolbar_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        horizontalScrollView = findViewById(R.id.scrollView);
    }

    public void setColorTint(int colorTint) {
        LinearLayout scrollView = (LinearLayout)
                ((HorizontalScrollView) getChildAt(0)).getChildAt(0);
        int children = scrollView.getChildCount();
        for (int i = 0; i < children; i++) {
            ImageView imageView = (ImageView) scrollView.getChildAt(i);
            imageView.setColorFilter(colorTint,
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setUpWithEditor(final RichEditor mEditor) {
        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu heading_popup = new PopupMenu(getContext(), v);
                heading_popup.getMenuInflater().inflate(R.menu.heading_menu, heading_popup.getMenu());
                heading_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_heading1) {
                            mEditor.setHeading(1);
                        }
                        if (item.getItemId() == R.id.action_heading2) {
                            mEditor.setHeading(2);
                        }
                        if (item.getItemId() == R.id.action_heading3) {
                            mEditor.setHeading(3);
                        }
                        if (item.getItemId() == R.id.action_heading4) {
                            mEditor.setHeading(4);
                        }
                        if (item.getItemId() == R.id.action_heading5) {
                            mEditor.setHeading(5);
                        }
                        if (item.getItemId() == R.id.action_heading6) {
                            mEditor.setHeading(6);
                        }
                        return true;
                    }
                });
                heading_popup.show();
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_selection(mEditor, true);
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_selection(mEditor, false);
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu Indent_popup = new PopupMenu(context, v);
                Indent_popup.getMenuInflater().inflate(R.menu.indent_menu, Indent_popup.getMenu());
                Indent_popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_indent) {
                            mEditor.setIndent();
                        }
                        if (item.getItemId() == R.id.action_outdent) {
                            mEditor.setOutdent();
                        }
                        if (item.getItemId() == R.id.action_align_left) {
                            mEditor.setAlignLeft();
                        }
                        if (item.getItemId() == R.id.action_align_center) {
                            mEditor.setAlignCenter();
                        }
                        if (item.getItemId() == R.id.action_align_right) {
                            mEditor.setAlignRight();
                        }
                        return true;
                    }
                });
                Indent_popup.show();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu List_popoup = new PopupMenu(context, v);
                List_popoup.getMenuInflater().inflate(R.menu.list_menu, List_popoup.getMenu());
                List_popoup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_insert_bullet) {
                            mEditor.setBullets();
                        }
                        if (item.getItemId() == R.id.action_insert_numbers) {
                            mEditor.setNumbers();
                        }
                        return true;
                    }
                });
                List_popoup.show();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText Image_Link;

                LayoutInflater LI = LayoutInflater.from(context);
                View PromptsView = LI.inflate(R.layout.image_dialog, null);

                Image_Link = (EditText) PromptsView.findViewById(R.id.Image_Link);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(PromptsView);
                alertDialogBuilder
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mEditor.insertImage(Image_Link.getText().toString(), "Image");
                            }
                        })
                        .setNegativeButton("Choose from Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (listener != null) {
                                    listener.onChoosePick();
                                }
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText Href_Link, Href_Title;

                LayoutInflater LI = LayoutInflater.from(context);
                View PromptsView = LI.inflate(R.layout.href_dailog, null);

                Href_Link = (EditText) PromptsView.findViewById(R.id.Href_Link);
                Href_Title = (EditText) PromptsView.findViewById(R.id.Href_Title);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setView(PromptsView);
                alertDialogBuilder
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mEditor.insertLink(Href_Link.getText().toString(), Href_Title.getText().toString());

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }

    public void color_selection(final RichEditor mEditor, final boolean choice) {

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(context, Color.RED, false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                //SampleActivity.this.color = color;
                if (choice == true) {
                    mEditor.setTextColor(color);
                }
                if (choice == false) {
                    mEditor.setTextBackgroundColor(color);
                }

            }
        });
        dialog.show();
    }

    private OnImagePickListener listener;

    public void setImagePickListener(OnImagePickListener listener) {
        this.listener = listener;
    }

    public interface OnImagePickListener {
        void onChoosePick();
    }
}
